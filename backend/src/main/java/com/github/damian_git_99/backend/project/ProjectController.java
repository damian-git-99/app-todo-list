package com.github.damian_git_99.backend.project;

import com.github.damian_git_99.backend.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.project.services.ProjectService;
import com.github.damian_git_99.backend.security.AuthenticatedUser;
import com.github.damian_git_99.backend.utils.BindingResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createProject(
            @Valid @RequestBody ProjectRequest projectRequest
            , Authentication authentication
            , BindingResult result) {

        if (result.hasErrors()) {
            var error =  BindingResultUtils.getFirstError(result);

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


}
