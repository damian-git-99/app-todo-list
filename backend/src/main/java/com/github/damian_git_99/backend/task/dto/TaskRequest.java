package com.github.damian_git_99.backend.task.dto;

import com.github.damian_git_99.backend.task.models.Priority;
import com.github.damian_git_99.backend.task.models.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Task} entity
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskRequest implements Serializable {
    @NotEmpty
    private String taskName;
    @NotEmpty
    private String description;
    @NotNull
    private Priority priority;
}