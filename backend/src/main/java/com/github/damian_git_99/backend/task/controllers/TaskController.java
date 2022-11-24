package com.github.damian_git_99.backend.task.controllers;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.services.TaskService;
import com.github.damian_git_99.backend.utils.BindingResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<?> createTask(@PathVariable(name = "projectId") Long projectId
            , @RequestBody TaskRequest taskRequest
            , BindingResult result
            , Authentication authentication) {

        if (result.hasErrors()) {
            var error = BindingResultUtils.getFirstError(result);
        }

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        taskService.createTask(authenticatedUser, projectId, taskRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
