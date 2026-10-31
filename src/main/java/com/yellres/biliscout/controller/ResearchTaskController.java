package com.yellres.biliscout.controller;
import org.springframework.web.bind.annotation.RestController;
import com.yellres.biliscout.mapper.ResearchTaskMapper;
import com.yellres.biliscout.entity.ResearchTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.yellres.biliscout.dto.CreateResearchTaskRequest;
import com.yellres.biliscout.entity.TaskStatus;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.net.URI;

@RestController
public class ResearchTaskController {
    private final ResearchTaskMapper researchTaskMapper;

    public ResearchTaskController(ResearchTaskMapper researchTaskMapper) {
        this.researchTaskMapper = researchTaskMapper;
    }

    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<ResearchTask> getById(@PathVariable("id") Long id) {
        ResearchTask researchTask = researchTaskMapper.selectById(id);
        if (researchTask == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(researchTask);
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<ResearchTask> create(
        @RequestBody CreateResearchTaskRequest request
    ) {
        ResearchTask task = new ResearchTask();
        task.setQuestion(request.question());
        task.setStatus(TaskStatus.QUEUED);
        task.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));

        researchTaskMapper.insert(task);
        ResearchTask saved = researchTaskMapper.selectById(task.getId());

        return ResponseEntity.created(URI.create("/api/tasks/" + task.getId())).body(saved);
    }

}
