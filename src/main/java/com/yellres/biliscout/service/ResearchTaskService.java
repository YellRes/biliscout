package com.yellres.biliscout.service;

import org.springframework.stereotype.Service;
import com.yellres.biliscout.mapper.ResearchTaskMapper;
import com.yellres.biliscout.entity.ResearchTask;
import com.yellres.biliscout.entity.TaskStatus;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import com.yellres.biliscout.exception.TaskNotFoundException;


@Service
public class ResearchTaskService {
    private final ResearchTaskMapper researchTaskMapper;

    public ResearchTaskService(ResearchTaskMapper researchTaskMapper) {
        this.researchTaskMapper = researchTaskMapper;
    }

    public ResearchTask getById(Long id) {
        ResearchTask task = researchTaskMapper.selectById(id);

        if (task == null) {
            throw new TaskNotFoundException(id);
        }

        return task;
    }

    public ResearchTask create(String question) {
        ResearchTask task = new ResearchTask();
        task.setQuestion(question);
        task.setStatus(TaskStatus.QUEUED);
        task.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));

        researchTaskMapper.insert(task);
        ResearchTask saved = researchTaskMapper.selectById(task.getId());

        return saved;
    }

}
