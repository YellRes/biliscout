package com.yellres.biliscout.bilibili;

import com.yellres.biliscout.entity.Video;

import tools.jackson.databind.JsonNode;

public class VideoConverter {
    // 搜索结果的一条 → Video；时间由调用方设置
    public static Video fromSearchItem(JsonNode item) {

        String originTitle = item.get("title").asString();
        String bvid = item.get("bvid").asString();
        // 左边是 B 站的字段名，不是我们的字段名
        Long play = item.get("play").asLong();
        Long like = item.get("like").asLong();

        Video video = new Video();
        video.setTitle(originTitle.replaceAll("<[^>]+>", ""));
        video.setBvid(bvid);
        video.setPlayCount(play);
        video.setLikeCount(like);
        video.setMid(item.get("mid").asLong());
        video.setAuthor(item.get("author").asString());
        video.setDescription(item.get("description").asString());
        video.setPubdate(item.get("pubdate").asLong());

        return video;
    }
}
