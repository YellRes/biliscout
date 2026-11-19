CREATE TABLE video_subtitle(
    video_id BIGINT NOT NULL,
    start_ms BIGINT NOT NULL,
    end_ms BIGINT NOT NULL,
    content TEXT NOT NULL,
    UNIQUE (video_id, start_ms, end_ms),
    FOREIGN KEY(video_id) REFERENCES video(id)
);