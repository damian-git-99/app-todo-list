package com.github.damian_git_99.backend.project.dto;

import com.github.damian_git_99.backend.project.models.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * A DTO for the {@link Project} entity
 */
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProjectRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}