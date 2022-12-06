package com.github.damian_git_99.backend.project.services;

import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.project.exceptions.ForbiddenProjectException;
import com.github.damian_git_99.backend.project.exceptions.ProjectNameAlreadyExists;
import com.github.damian_git_99.backend.project.exceptions.ProjectNotFoundException;
import com.github.damian_git_99.backend.project.daos.ProjectDao;
import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.user.models.User;
import com.github.damian_git_99.backend.user.exceptions.UserNotFoundException;
import com.github.damian_git_99.backend.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final UserService userService;
    private final ProjectDao projectDao;

    @Autowired
    public ProjectServiceImpl(UserService userService, ProjectDao projectDao) {
        this.userService = userService;
        this.projectDao = projectDao;
    }

    @Override
    public void createProject(ProjectRequest projectRequest, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Optional<Project> projectOptional = user.getProjects().stream()
                .filter(project -> project.getName().equals(projectRequest.getName()))
                .findFirst();

        if (projectOptional.isPresent()) {
            throw new ProjectNameAlreadyExists("Project name already exists");
        }

        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setCreatedAt(LocalDateTime.now());
        project.setUser(user);

        projectDao.save(project);
    }

    @Override
    public Project findProjectById(Long projectId, AuthenticatedUser authenticatedUser) {
        Project project = projectDao.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));

        boolean projectBelongsToTheAuthenticatedUser = project
                .getUser().getId().equals(authenticatedUser.getId());

        if (!projectBelongsToTheAuthenticatedUser) {
            throw new ForbiddenProjectException("Project does not belong to the authenticated user");
        }

        return project;
    }

    @Override
    public void deleteProjectById(Long projectId, AuthenticatedUser authenticatedUser) {
        Project project = this.findProjectById(projectId, authenticatedUser);
        projectDao.delete(project);
    }

}
