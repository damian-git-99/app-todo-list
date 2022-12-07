package com.github.damian_git_99.backend.project.dto;

import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.task.dto.TaskResponseConverter;
import com.github.damian_git_99.backend.utils.AbstractConverter;

public class ProjectDetailsConverter implements AbstractConverter<Project, ProjectDetailsResponse> {

    private final TaskResponseConverter converter = new TaskResponseConverter();

    @Override
    public Project toEntity(ProjectDetailsResponse dto) {
        return Project.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .tasks(converter.toEntities(dto.getTasks()))
                .build();
    }

    @Override
    public ProjectDetailsResponse toDto(Project entity) {
        return ProjectDetailsResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .tasks(converter.toDtos(entity.getTasks()))
                .build();
    }

}
