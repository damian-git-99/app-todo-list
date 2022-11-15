package com.github.damian_git_99.backend.project.dto;

import com.github.damian_git_99.backend.project.models.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    private final Date createdAt;
}