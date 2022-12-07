package com.github.damian_git_99.backend.task.services;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.models.Task;

public interface TaskService {

    Task createTask(AuthenticatedUser user, Long projectId, TaskRequest task);
    void deleteTaskById(AuthenticatedUser user, Long projectId, Long taskId);
    void toggleTask(AuthenticatedUser user, Long projectId, Long taskId);

}
