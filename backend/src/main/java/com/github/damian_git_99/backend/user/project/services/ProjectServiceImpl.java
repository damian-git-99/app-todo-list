package com.github.damian_git_99.backend.user.project.services;

import com.github.damian_git_99.backend.user.project.Project;
import com.github.damian_git_99.backend.user.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.user.entities.User;
import com.github.damian_git_99.backend.user.exceptions.UserNotFoundException;
import com.github.damian_git_99.backend.user.project.exceptions.ProjectNameAlreadyExists;
import com.github.damian_git_99.backend.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final UserService userService;

    @Autowired
    public ProjectServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createProject(ProjectRequest projectRequest, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Optional<Project> projectOptional = user.getProjects().stream().filter(project -> project.getName().equals(projectRequest.getName()))
                .findFirst();

        if (projectOptional.isPresent()) {
            throw new ProjectNameAlreadyExists("Project name already exists");
        }

        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setCreatedAt(new Date());

        user.addProject(project);
    }

}
