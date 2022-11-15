package com.github.damian_git_99.backend.project.services;

import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;

public interface ProjectService {

    void createProject(ProjectRequest projectRequest, Long userId);
    Project findProjectById(Long projectId, AuthenticatedUser authenticatedUser);
    void deleteProjectById(Long projectId, AuthenticatedUser authenticatedUser);

}
