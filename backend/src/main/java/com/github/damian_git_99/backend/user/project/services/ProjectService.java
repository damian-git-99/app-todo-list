package com.github.damian_git_99.backend.user.project.services;

import com.github.damian_git_99.backend.user.project.dto.ProjectRequest;

public interface ProjectService {

    void createProject(ProjectRequest projectRequest, Long userId);

}
