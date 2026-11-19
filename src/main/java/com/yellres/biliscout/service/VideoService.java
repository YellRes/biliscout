package com.yellres.biliscout.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yellres.biliscout.bilibili.BilibiliClient;
import com.yellres.biliscout.bilibili.SubtitleCue;
import com.yellres.biliscout.bilibili.VideoConverter;
import com.yellres.biliscout.entity.Video;
import com.yellres.biliscout.exception.BilibiliApiException;
import com.yellres.biliscout.mapper.SubtitleMapper;
import com.yellres.biliscout.mapper.VideoMapper;

import tools.jackson.databind.JsonNode;

@Service
public class VideoService {
    private final BilibiliClient bilibiliClient;
    private final VideoMapper videoMapper;
    private final SubtitleMapper subtitleMapper;

    public VideoService(BilibiliClient bilibiliClient, VideoMapper videoMapper, SubtitleMapper subtitleMapper) {
        this.bilibiliClient = bilibiliClient;
        this.videoMapper = videoMapper;
        this.subtitleMapper = subtitleMapper;
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

    public void saveSubtitles(String bvid, List<SubtitleCue> cues) {
        Video video = videoMapper.selectByBvid(bvid);
        if (video == null) {
            throw new IllegalArgumentException("找不到视频，bvid=" + bvid);
        }

        for (SubtitleCue cue : cues) {
            long startMs = cue.startMs();
            long endMs = cue.endMs();
            subtitleMapper.upsert(startMs, endMs, cue.text(), video.getId());
        }
    }
}