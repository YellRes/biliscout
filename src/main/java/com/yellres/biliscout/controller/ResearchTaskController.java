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
import com.yellres.biliscout.service.ResearchTaskService;
import com.yellres.biliscout.common.Result;
import jakarta.validation.Valid;

@RestController
public class ResearchTaskController {
    // private final ResearchTaskMapper researchTaskMapper;
    private final ResearchTaskService researchTaskService;


    public ResearchTaskController(ResearchTaskService researchTaskService) {
        this.researchTaskService = researchTaskService;
    }

    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<Result<ResearchTask>> getById(@PathVariable("id") Long id) {
        ResearchTask researchTask = researchTaskService.getById(id);
        return ResponseEntity.ok(new Result<>(0, "success", researchTask));
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<Result<ResearchTask>> create(
        @Valid @RequestBody CreateResearchTaskRequest request
    ) {
        ResearchTask saved = researchTaskService.create(request.question());

        return ResponseEntity.created(URI.create("/api/tasks/" + saved.getId())).body(
            new Result<>(0, "success", saved)
        );
    }

}
