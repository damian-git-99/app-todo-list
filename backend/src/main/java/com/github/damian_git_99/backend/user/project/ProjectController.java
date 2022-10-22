package com.github.damian_git_99.backend.user.project;

import com.github.damian_git_99.backend.user.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.user.project.services.ProjectService;
import com.github.damian_git_99.backend.security.AuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity createProject(@Valid @RequestBody ProjectRequest projectRequest, Authentication authentication, BindingResult result) {
        // todo refactor this code
        if (result.hasErrors()) {
            var firstError = result.getFieldErrors().stream().findFirst().orElseThrow();
            Map<String, Object> response = new HashMap<>();
            response.put("error", firstError.getDefaultMessage());

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        projectService.createProject(projectRequest, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }


}
