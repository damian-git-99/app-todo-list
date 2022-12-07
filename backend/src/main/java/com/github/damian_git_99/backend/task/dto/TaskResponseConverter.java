package com.github.damian_git_99.backend.task.dto;

import com.github.damian_git_99.backend.task.models.Task;
import com.github.damian_git_99.backend.utils.AbstractConverter;

public class TaskResponseConverter implements AbstractConverter<Task, TaskResponse> {

    @Override
    public Task toEntity(TaskResponse dto) {
        return Task.builder()
                .id(dto.getId())
                .taskName(dto.getTaskName())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .isComplete(dto.isComplete())
                .build();
    }

    @Override
    public TaskResponse toDto(Task entity) {
        return TaskResponse.builder()
                .id(entity.getId())
                .taskName(entity.getTaskName())
                .description(entity.getDescription())
                .priority(entity.getPriority())
                .isComplete(entity.isComplete())
                .build();
    }

}
