package com.yellres.biliscout.bilibili;

import com.yellres.biliscout.exception.BilibiliApiException;
import tools.jackson.databind.JsonNode;

public class VideoDetailConverter {
    public static VideoDetail fromResponse(JsonNode response) {
        if (response == null || !response.path("code").isIntegralNumber()) {
            throw new IllegalArgumentException("视频详情响应缺少有效的 code");
        }

        int code = response.path("code").asInt();
        if (code != 0) {
            String message = response.path("message").asString("获取视频详情失败");
            throw new BilibiliApiException(code, message);
        }

        JsonNode data = response.path("data");
        long aid = data.path("aid").asLong(0);
        long cid = data.path("pages").path(0).path("cid").asLong(0);

        if (aid <= 0 || cid <= 0) {
            throw new IllegalArgumentException("视频详情缺少有效的 aid 或 cid");
        }

        return new VideoDetail(aid, cid);
    }
}
