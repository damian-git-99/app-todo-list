package com.github.damian_git_99.backend.project.dto;

import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.task.dto.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
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
    private final Date createdAt;
    private final List<TaskResponse> tasks;
}