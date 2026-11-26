package com.yellres.biliscout.bilibili;

import tools.jackson.databind.JsonNode;

public class CommentConverter {
    public static VideoComment fromItem(JsonNode item) {
        long commentId = item.path("rpid").asLong();
        String content = item.path("content").path("message").asString();
        long postedAt = item.path("ctime").asLong();

        return new VideoComment(commentId, content, postedAt);

    }
}
