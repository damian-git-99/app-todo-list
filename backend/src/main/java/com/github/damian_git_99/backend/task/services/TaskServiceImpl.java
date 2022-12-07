package com.github.damian_git_99.backend.task.services;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.project.services.ProjectService;
import com.github.damian_git_99.backend.task.daos.TaskDao;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.exceptions.ForbiddenTaskException;
import com.github.damian_git_99.backend.task.exceptions.TaskNotFoundException;
import com.github.damian_git_99.backend.task.models.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final ProjectService projectService;
    private final TaskDao taskDao;

    @Autowired
    public TaskServiceImpl(ProjectService projectService, TaskDao taskDao) {
        this.projectService = projectService;
        this.taskDao = taskDao;
    }

    @Override
    public Task createTask(AuthenticatedUser user
            , Long projectId
            , TaskRequest taskRequest) {
        Project project = projectService.findProjectById(projectId, user);
        Task task = Task.builder()
                .taskName(taskRequest.getTaskName())
                .description(taskRequest.getDescription())
                .priority(taskRequest.getPriority())
                .project(project)
                .build();
        return taskDao.save(task);
    }

    @Override
    public void deleteTaskById(AuthenticatedUser user, Long projectId, Long taskId) {
        Project project = projectService.findProjectById(projectId, user);

        Task task = taskDao.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        boolean isTaskInProject = task.getProject().equals(project);

        if (!isTaskInProject) {
            throw new ForbiddenTaskException("Forbidden operation");
        }

        taskDao.deleteById(taskId);
    }

    @Override
    public void toggleTask(AuthenticatedUser user, Long projectId, Long taskId) {
        Project project = projectService.findProjectById(projectId, user);

        Task task = taskDao.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        boolean isTaskInProject = task.getProject().equals(project);

        if (!isTaskInProject) {
            throw new ForbiddenTaskException("Forbidden operation");
        }

        boolean toggle = !task.isComplete();
        task.setComplete(toggle);
    }

}
