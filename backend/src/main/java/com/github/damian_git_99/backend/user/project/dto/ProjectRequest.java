package com.github.damian_git_99.backend.user.project.dto;

import com.github.damian_git_99.backend.user.project.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * A DTO for the {@link Project} entity
 */
@Data
@NoArgsConstructor
public class ProjectRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}