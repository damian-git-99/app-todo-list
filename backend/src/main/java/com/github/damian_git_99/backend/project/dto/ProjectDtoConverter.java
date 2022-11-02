package com.github.damian_git_99.backend.project.dto;

import com.github.damian_git_99.backend.project.Project;
import com.github.damian_git_99.backend.utils.AbstractConverter;

public class ProjectDtoConverter implements AbstractConverter<Project, ProjectResponse> {

    @Override
    public Project toEntity(ProjectResponse projectResponse) {
        return Project.builder()
                .id(projectResponse.getId())
                .name(projectResponse.getName())
                .description(projectResponse.getDescription())
                .createdAt(projectResponse.getCreatedAt())
                .build();
    }

    @Override
    public ProjectResponse toDto(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .build();
    }

}
