package com.yellres.biliscout.bilibili;

import tools.jackson.databind.JsonNode;

public class SubtitleConverter {
    public static SubtitleCue fromItem(JsonNode item) {
        double startSecond = item.get("from").asDouble();
        double endSecond = item.get("to").asDouble();
        String text = item.get("content").asString();

        return new SubtitleCue(startSecond, endSecond, text);
    }
}
