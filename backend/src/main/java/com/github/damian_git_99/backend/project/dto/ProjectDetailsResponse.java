package com.github.damian_git_99.backend.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.task.dto.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link Project} entity
 */
@Builder
@AllArgsConstructor
@Getter
public class ProjectDetailsResponse implements Serializable {
    private final Long id;
    private final String name;
    private final String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    private final List<TaskResponse> tasks;
}