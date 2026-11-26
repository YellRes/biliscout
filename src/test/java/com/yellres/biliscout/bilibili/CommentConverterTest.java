package com.yellres.biliscout.bilibili;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;

public class CommentConverterTest {
    @Test
    void testVideoComment() {
        String str = """
                { "rpid": 123456, "ctime": 1700000000, "content": { "message": "测试评论" } }
                """;
        JsonNode item = new ObjectMapper().readTree(str);
        VideoComment cmt = CommentConverter.fromItem(item);
        assertEquals(123456, cmt.commentId());
        assertEquals("测试评论", cmt.content());
        assertEquals(1700000000, cmt.postedAt());
    }

    @Test
    void testVideoCommentWithoutMessage() {
        String str = """
                { "rpid": 123456, "ctime": 1700000000}
                """;
        JsonNode item = new ObjectMapper().readTree(str);
        VideoComment cmt = CommentConverter.fromItem(item);
        assertEquals(123456, cmt.commentId());
        assertEquals("", cmt.content());
        assertEquals(1700000000, cmt.postedAt());
    }
}
