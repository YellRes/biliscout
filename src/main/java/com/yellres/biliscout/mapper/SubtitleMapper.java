package com.yellres.biliscout.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yellres.biliscout.bilibili.SubtitleCue;

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

    @Insert("""
            <script>
                INSERT INTO video_subtitle (start_ms, end_ms, content, video_id)
                VALUES
                <foreach collection="cues" item="cue" separator=",">
                    (#{cue.startMs}, #{cue.endMs}, #{cue.text}, #{videoId})
                </foreach>
                AS new
                ON DUPLICATE KEY UPDATE content = new.content
            </script>
            """)
    int upsertBatch(@Param("videoId") long videoId, @Param("cues") List<SubtitleCue> cues);
}
