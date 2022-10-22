package com.github.damian_git_99.backend.user.project.dto;

import com.github.damian_git_99.backend.user.project.Project;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link Project} entity
 */
@Data
public class ProjectRequest implements Serializable {
    private final String name;
    private final String description;
    private final Date createdAt;
}