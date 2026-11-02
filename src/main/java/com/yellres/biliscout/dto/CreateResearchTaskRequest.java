package com.yellres.biliscout.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateResearchTaskRequest(
    @NotBlank(message = "问题不能为空")
    @Size(max = 500, message = "问题不能超过500个字符")
    String question
) {}
