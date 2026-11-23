package com.yellres.biliscout.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yellres.biliscout.bilibili.BilibiliClient;
import com.yellres.biliscout.bilibili.SubtitleCue;
import com.yellres.biliscout.bilibili.VideoComment;
import com.yellres.biliscout.bilibili.VideoConverter;
import com.yellres.biliscout.entity.Video;
import com.yellres.biliscout.exception.BilibiliApiException;
import com.yellres.biliscout.mapper.CommentMapper;
import com.yellres.biliscout.mapper.SubtitleMapper;
import com.yellres.biliscout.mapper.VideoMapper;

import tools.jackson.databind.JsonNode;

@Service
public class VideoService {
    private static final Logger log = LoggerFactory.getLogger(VideoService.class);

    private final BilibiliClient bilibiliClient;
    private final VideoMapper videoMapper;
    private final SubtitleMapper subtitleMapper;
    private final CommentMapper commentMapper;

    public VideoService(BilibiliClient bilibiliClient, VideoMapper videoMapper, SubtitleMapper subtitleMapper,
            CommentMapper commentMapper) {
        this.bilibiliClient = bilibiliClient;
        this.videoMapper = videoMapper;
        this.subtitleMapper = subtitleMapper;
        this.commentMapper = commentMapper;
    }

    public List<Video> searchAndSave(String keyword) {
        JsonNode res = bilibiliClient.search(keyword);
        int code = res.get("code").asInt();
        if (code != 0)
            throw new BilibiliApiException(code, res.get("message").asString());
        JsonNode items = res.get("data").get("result");
        List<Video> videos = new ArrayList<>();

        for (JsonNode item : items) {
            Video video = VideoConverter.fromSearchItem(item);
            long now = System.currentTimeMillis() / 1000;
            video.setCreatedAt(now);
            video.setUpdatedAt(now);
            videos.add(video);
            videoMapper.upsert(video);
        }

        return videos;

    }

    @Transactional
    public void saveSubtitles(String bvid, List<SubtitleCue> cues) {
        Video video = videoMapper.selectByBvid(bvid);
        if (video == null) {
            throw new IllegalArgumentException("找不到视频，bvid=" + bvid);
        }

        subtitleMapper.deleteByVideoId(video.getId());
        if (cues.isEmpty())
            return;
        subtitleMapper.upsertBatch(video.getId(), cues);
    }

    public void saveComments(String bvid, List<VideoComment> comments) {
        List<VideoComment> validComments = comments.stream().filter(comment -> comment.content() != null).toList();

        Video video = videoMapper.selectByBvid(bvid);
        if (video == null) {
            throw new IllegalArgumentException("找不到视频，bvid=" + bvid);
        }

        int skippedCount = comments.size() - validComments.size();
        if (skippedCount > 0) {
            log.warn("视频 {} 跳过了 {} 条正文为 null 的评论", bvid, skippedCount);
        }

        if (validComments.isEmpty())
            return;
        commentMapper.upsertBatch(video.getId(), validComments);
    }
}
