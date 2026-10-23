package com.yellres.biliscout.repository;
import com.yellres.biliscout.entity.ResearchTask;
import java.util.Optional;

public interface ResearchTaskRepository {

    Long save(ResearchTask task);
    Optional<ResearchTask> get(Long taskId);
}