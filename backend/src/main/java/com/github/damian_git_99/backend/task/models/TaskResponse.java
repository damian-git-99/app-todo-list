package com.github.damian_git_99.backend.task.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link Task} entity
 */
@AllArgsConstructor
@Getter
public class TaskResponse implements Serializable {
    private final String taskName;
    private final String description;
    private final Priority priority;
}