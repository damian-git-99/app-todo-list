package com.github.damian_git_99.backend.project.services;

import com.github.damian_git_99.backend.project.dto.ProjectRequest;

public interface ProjectService {

    void createProject(ProjectRequest projectRequest, Long userId);

}
