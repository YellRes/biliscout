package com.yellres.biliscout.bilibili;

import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import tools.jackson.databind.JsonNode;

public class SubtitleConverterTest {
    @Test
    void convertsItemToCue() {
        String json = """
                    {
                        "from": 12.34, "to": 15.6, "content": "测试字幕"
                    }
                """;
        JsonNode item = new ObjectMapper().readTree(json);
        SubtitleCue subtitleCue = SubtitleConverter.fromItem(item);
        assertEquals("测试字幕", subtitleCue.text());
        assertEquals(12340, subtitleCue.startMs());
        assertEquals(15600, subtitleCue.endMs());
    }
}
