package com.yellres.biliscout.entity;
import java.time.LocalDateTime;

public record ResearchTask(
    Long id,
    TaskStatus status,
    LocalDateTime createdAt,
    String question
) {}