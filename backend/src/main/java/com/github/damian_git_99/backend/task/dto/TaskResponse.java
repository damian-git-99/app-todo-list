package com.github.damian_git_99.backend.task.dto;

import com.github.damian_git_99.backend.task.models.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.github.damian_git_99.backend.task.models.Task} entity
 */
@Builder
@AllArgsConstructor
@Getter
public class TaskResponse implements Serializable {
    private final Long id;
    private final String taskName;
    private final String description;
    private final Priority priority;
    private boolean isComplete;
}