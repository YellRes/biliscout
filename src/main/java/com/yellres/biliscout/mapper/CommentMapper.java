package com.yellres.biliscout.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yellres.biliscout.bilibili.VideoComment;

@Mapper
public interface CommentMapper {
    @Insert("""
            <script>
                INSERT INTO video_comment (comment_id, content, posted_at, video_id)
                VALUES
                <foreach collection="comments" item="comment" separator=",">
                    (#{comment.commentId}, #{comment.content}, #{comment.postedAt}, #{videoId})
                </foreach>
                AS new
                ON DUPLICATE KEY UPDATE content = new.content
            </script>
            """)
    int upsertBatch(@Param("videoId") long videoId, @Param("comments") List<VideoComment> comments);
}
