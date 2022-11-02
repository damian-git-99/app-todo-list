package com.github.damian_git_99.backend.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link com.github.damian_git_99.backend.project.Project} entity
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