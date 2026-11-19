package com.yellres.biliscout.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yellres.biliscout.entity.Video;

@Mapper
public interface VideoMapper {
    @Insert("""
            INSERT INTO video (bvid, title, play_count, like_count, mid, author, description, created_at, updated_at, pubdate)
            VALUES (#{bvid}, #{title}, #{playCount}, #{likeCount}, #{mid}, #{author}, #{description}, #{createdAt}, #{updatedAt}, #{pubdate}) AS new
            ON DUPLICATE KEY UPDATE title = new.title,
            play_count = new.play_count,
            like_count = new.like_count,
            mid = new.mid,
            author = new.author,
            updated_at = new.updated_at,
            description = new.description,
            pubdate = new.pubdate
            """)
    int upsert(Video video);

    @Select("""
            SELECT id, bvid, title, play_count as playCount, like_count as likeCount, mid, author, description, created_at as createdAt, updated_at as updatedAt, pubdate
            FROM video
            WHERE bvid = #{bvid}
            """)
    Video selectByBvid(@Param("bvid") String bvid);

}
