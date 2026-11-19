package com.yellres.biliscout.bilibili;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.yellres.biliscout.entity.Video;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;

public class VideoConverterTest {

    @Test
    void convertsSearchItemToVideo() {
        String json = """
                {
                  "bvid": "BV1xx411c7mD",
                  "title": "<em class=\\"keyword\\">side</em> sleep",
                  "like": 678,
                  "mid": 9527,
                  "play": "12345",
                  "author": "test author",
                  "description": "test description",
                  "pubdate": 1700000000
                }
                """;
        JsonNode item = new ObjectMapper().readTree(json);
        Video video = VideoConverter.fromSearchItem(item);

        assertEquals("BV1xx411c7mD", video.getBvid());
        assertEquals("side sleep", video.getTitle());
        assertEquals(12345L, video.getPlayCount());
        assertEquals(678L, video.getLikeCount());
        assertEquals(9527L, video.getMid());
        assertEquals("test author", video.getAuthor());
        assertEquals("test description", video.getDescription());
        assertEquals(1700000000L, video.getPubdate());
        // 时间不归转换管
        assertNull(video.getCreatedAt());
        assertNull(video.getUpdatedAt());

    }

    @Test
    void convertsSearchItemToVideo2() {
        String json = """
                {
                  "bvid": "BV1xx411c7mD",
                  "title": "<em class=\\"keyword\\">side</em> sleep",
                  "like": 678,
                  "mid": 9527,
                  "author": "test author",
                  "description": "test description",
                  "pubdate": 1700000000
                }
                """;
        JsonNode item = new ObjectMapper().readTree(json);
        assertThrows(NullPointerException.class, () -> VideoConverter.fromSearchItem(item));
    }
}
