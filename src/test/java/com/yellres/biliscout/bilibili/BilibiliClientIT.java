package com.yellres.biliscout.bilibili;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

public class BilibiliClientIT {
    @Test
    void testClient() {
        BilibiliClient client = new BilibiliClient(RestClient.builder(), "");
        WbiKeys keys = client.fetchWbiKeys();

        assertEquals(32, keys.imgKey().length());
        assertEquals(32, keys.subKey().length());

    }

    @Test
    void testSearch() {
        BilibiliClient client = new BilibiliClient(RestClient.builder(), "");

        for (int i = 0; i < 5; i++) {
            JsonNode result = client.search("侧睡 枕头");
            assertEquals(0, result.get("code").intValue(), result.toString());

            JsonNode videos = result.get("data").get("result");
            assertTrue(videos.isArray());
            assertTrue(videos.size() >= 20, "实际返回条数：" + videos.size());
        }

    }
}
