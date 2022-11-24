package com.github.damian_git_99.backend.task.services;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.models.Task;

public interface TaskService {

    public Task createTask(AuthenticatedUser user, Long projectId, TaskRequest task);

}
