package com.github.damian_git_99.backend.task.controllers;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.services.TaskService;
import com.github.damian_git_99.backend.utils.BindingResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Tasks")
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "This method is used to create a task")
    @PostMapping("/{projectId}")
    public ResponseEntity<?> createTask(@PathVariable(name = "projectId") Long projectId
            , @RequestBody @Valid TaskRequest taskRequest
            , BindingResult result
            , Authentication authentication) {

        if (result.hasErrors()) {
            var error = BindingResultUtils.getFirstError(result);
            return ResponseEntity
                    .badRequest()
                    .body(error);
        }

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        taskService.createTask(authenticatedUser, projectId, taskRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @ApiOperation(value = "This method is used to delete a task")
    @DeleteMapping("/{projectId}/{taskId}")
    public void deleteTask(@PathVariable(name = "projectId") Long projectId
            , @PathVariable(name = "taskId") Long taskId
            , Authentication authentication) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        taskService.deleteTaskById(authenticatedUser, projectId, taskId);
    }

    @ApiOperation(value = "This method is used to toggle the isComplete property task")
    @PutMapping("/{projectId}/{taskId}")
    public void toggleTask(@PathVariable(name = "projectId") Long projectId
            , @PathVariable(name = "taskId") Long taskId
            , Authentication authentication) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        taskService.toggleTask(authenticatedUser, projectId, taskId);
    }


}
