package com.github.damian_git_99.backend.task.dto;

import com.github.damian_git_99.backend.task.models.Priority;
import com.github.damian_git_99.backend.task.models.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * A DTO for the {@link Task} entity
 */
@AllArgsConstructor
@Getter
public class TaskRequest implements Serializable {
    @NotEmpty
    private final String taskName;
    @NotEmpty
    private final String description;
    @NotEmpty
    private final Priority priority;
}