package com.yellres.biliscout.service;

import org.springframework.stereotype.Service;

import com.yellres.biliscout.bilibili.BilibiliClient;
import com.yellres.biliscout.bilibili.VideoDetail;
import com.yellres.biliscout.bilibili.VideoDetailConverter;

import tools.jackson.databind.JsonNode;

@Service
public class VideoCollectionService {
    private final BilibiliClient bilibiliClient;

    public VideoCollectionService(BilibiliClient bilibiliClient) {
        this.bilibiliClient = bilibiliClient;
    }

    public VideoDetail loadDetail(String bvid) {
        JsonNode response = bilibiliClient.fetchVideoDetail(bvid);
        return VideoDetailConverter.fromResponse(response);
    }
}
