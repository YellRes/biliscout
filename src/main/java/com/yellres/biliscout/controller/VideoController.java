package com.yellres.biliscout.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yellres.biliscout.common.Result;
import com.yellres.biliscout.service.VideoService;

import jakarta.validation.constraints.NotBlank;

import com.yellres.biliscout.entity.Video;
import java.util.List;

@RestController
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/api/videos/search")
    public ResponseEntity<Result<List<Video>>> search(@NotBlank @RequestParam("keyword") String keyword) {
        List<Video> videos = videoService.searchAndSave(keyword);

        return ResponseEntity.ok(new Result<>(0, "success", videos));
    }
}
