package com.github.damian_git_99.backend.project.controllers;

import com.github.damian_git_99.backend.project.dto.*;
import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.project.services.ProjectService;
import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.user.models.User;
import com.github.damian_git_99.backend.user.exceptions.UserNotFoundException;
import com.github.damian_git_99.backend.user.services.UserService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api( tags = "Projects")
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectDtoConverter projectConverter = new ProjectDtoConverter();
    private final ProjectDetailsConverter projectDetailsConverter = new ProjectDetailsConverter();

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @ApiOperation(value = "This method is used to create a project")
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createProject(
            @Valid @RequestBody ProjectRequest projectRequest
            , Authentication authentication
            , BindingResult result) {

        if (result.hasErrors()) {
            Map<String, Object> error = BindingResultUtils.getFirstError(result);

            return ResponseEntity
                    .badRequest()
                    .body(error);
        }

        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        projectService.createProject(projectRequest, user.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @ApiOperation(value = "This method is used to get the projects of a user")
    @GetMapping("")
    public List<ProjectResponse> findAllProjectByUser(Authentication authentication) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        User user = userService.findById(authenticatedUser.getId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found Exception"));

        return user.getProjects().stream()
                .map(projectConverter::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "This method is used to get the details of a project")
    @GetMapping("/{id}")
    public ProjectDetailsResponse findProjectById(@PathVariable(name = "id") Long id, Authentication authentication) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        Project project = projectService.findProjectById(id, authenticatedUser);
        return projectDetailsConverter.toDto(project);
    }

    @ApiOperation(value = "This method is used to delete a project")
    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable(name = "id") Long id, Authentication authentication) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        projectService.deleteProjectById(id, authenticatedUser);
    }


}
