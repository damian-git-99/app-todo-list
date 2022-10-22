package com.github.damian_git_99.backend.user.project;

import com.github.damian_git_99.backend.user.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.user.project.services.ProjectService;
import com.github.damian_git_99.backend.security.AuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody ProjectRequest projectRequest, Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        projectService.createProject(projectRequest, user.getId());
    }


}
