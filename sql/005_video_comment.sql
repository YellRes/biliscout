CREATE TABLE video_comment(
    video_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    posted_at BIGINT NOT NULL,
    UNIQUE (video_id, comment_id),
    FOREIGN KEY(video_id) REFERENCES video(id)
);