package com.yellres.biliscout.bilibili;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.yellres.biliscout.exception.BilibiliApiException;

import tools.jackson.databind.JsonNode;

import tools.jackson.databind.ObjectMapper;

public class VideoDetailConverterTest {
    @Test
    void testVideoDetail() {
        String str = """
                        {
                  "code": 0,
                  "data": {
                    "aid": 170001,
                    "pages": [ { "cid": 279786 } ]
                  }
                }
                        """;
        JsonNode item = new ObjectMapper().readTree(str);
        VideoDetail vd = VideoDetailConverter.fromResponse(item);
        assertEquals(170001, vd.aid());
        assertEquals(279786, vd.cid());
    }

    @Test
    void testVideoNotFound() {
        String str = """
                { "code": -404, "message": "啥都木有" }
                       """;
        JsonNode item = new ObjectMapper().readTree(str);
        assertThrows(BilibiliApiException.class, () -> VideoDetailConverter.fromResponse(item));
    }

    @Test
    void testBadResult() {
        String str = """
                { "code": 0, "data": { "aid": 170001 } }
                       """;
        JsonNode item = new ObjectMapper().readTree(str);
        assertThrows(IllegalArgumentException.class, () -> VideoDetailConverter.fromResponse(item));

    }
}
