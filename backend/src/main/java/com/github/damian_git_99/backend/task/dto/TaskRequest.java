package com.github.damian_git_99.backend.task.dto;

import com.github.damian_git_99.backend.task.models.Priority;
import com.github.damian_git_99.backend.task.models.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Task} entity
 */
@Builder
@AllArgsConstructor
@Getter
public class TaskRequest implements Serializable {
    @NotEmpty
    private final String taskName;
    @NotEmpty
    private final String description;
    @NotNull
    private final Priority priority;
}