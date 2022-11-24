package com.github.damian_git_99.backend.task.services;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.project.services.ProjectService;
import com.github.damian_git_99.backend.task.daos.TaskDao;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final ProjectService projectService;
    private final TaskDao taskDao;

    @Autowired
    public TaskServiceImpl(ProjectService projectService, TaskDao taskDao) {
        this.projectService = projectService;
        this.taskDao = taskDao;
    }

    @Override
    public Task createTask(AuthenticatedUser user, Long projectId, TaskRequest taskRequest) {
        Project project = projectService.findProjectById(projectId, user);
        Task task = Task.builder()
                .taskName(taskRequest.getTaskName())
                .description(taskRequest.getDescription())
                .priority(taskRequest.getPriority())
                .build();
        task.setProject(project);
        return taskDao.save(task);
    }

}
