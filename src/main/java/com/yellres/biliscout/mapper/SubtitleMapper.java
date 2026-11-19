package com.yellres.biliscout.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubtitleMapper {

    @Insert("""
            INSERT INTO video_subtitle (start_ms, end_ms, content, video_id)
            VALUES (#{startMs}, #{endMs}, #{content}, #{videoId}) AS new
            ON DUPLICATE KEY UPDATE content = new.content
            """)
    int upsert(
            @Param("startMs") long startMs,
            @Param("endMs") long endMs,
            @Param("content") String content,
            @Param("videoId") long videoId);
}
