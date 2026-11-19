package com.yellres.biliscout.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.yellres.biliscout.entity.Video;

@SpringBootTest
public class VideoMapperTest {
    @Autowired
    VideoMapper videoMapper;

    // 造一条测试数据，只有标题、播放量、时间不同
    private Video buildVideo(String title, Long playCount, Long time) {
        Video video = new Video();
        video.setBvid("BVtest001");
        video.setTitle(title);
        video.setPlayCount(playCount);
        video.setLikeCount(1L);
        video.setMid(123L);
        video.setAuthor("测试作者");
        video.setDescription("测试简介");
        video.setPubdate(1700000000L);
        video.setCreatedAt(time);
        video.setUpdatedAt(time);
        return video;
    }

    @Test
    @Transactional
    void upsertSameBvidUpdatesInsteadOfInserting() {
        videoMapper.upsert(buildVideo("旧标题", 10L, 1000L));
        Video first = videoMapper.selectByBvid("BVtest001");

        videoMapper.upsert(buildVideo("新标题", 99L, 2000L));
        Video second = videoMapper.selectByBvid("BVtest001");

        // 同一行，没有新插入
        assertEquals(first.getId(), second.getId());
        // 写在 UPDATE 里的：变成第二次的值
        assertEquals("新标题", second.getTitle());
        assertEquals(99L, second.getPlayCount());
        assertEquals(2000L, second.getUpdatedAt());
        // 没写在 UPDATE 里的：保持第一次的值
        assertEquals(1000L, second.getCreatedAt());
    }
}
