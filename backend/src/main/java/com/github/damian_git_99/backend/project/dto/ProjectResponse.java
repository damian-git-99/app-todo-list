package com.github.damian_git_99.backend.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.damian_git_99.backend.project.models.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Project} entity
 */
@Builder
@Data
@AllArgsConstructor
public class ProjectResponse implements Serializable {
    private final Long id;
    private final String name;
    private final String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
}