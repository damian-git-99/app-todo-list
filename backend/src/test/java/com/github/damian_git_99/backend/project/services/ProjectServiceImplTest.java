package com.github.damian_git_99.backend.project.services;

import com.github.damian_git_99.backend.project.Project;
import com.github.damian_git_99.backend.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.project.exceptions.ProjectNameAlreadyExists;
import com.github.damian_git_99.backend.user.entities.User;
import com.github.damian_git_99.backend.user.exceptions.UserNotFoundException;
import com.github.damian_git_99.backend.user.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {


    @Mock
    private UserService userService;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    @DisplayName("Should throw user not found exception when user does not exist")
    void shouldThrowUserNotFound() {
        ProjectRequest projectRequest = ProjectRequest.builder()
                .name("new project")
                .description("description")
                .build();

        given(userService.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.createProject(projectRequest, 1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User Not Found");

    }

    @Test
    @DisplayName("Should Throw ProjectNameAlreadyExists exception")
    void shouldThrowProjectNameAlreadyExists(){
        Project project = new Project("project1","description of my project");
        List<Project> projects = List.of(project);
        ProjectRequest projectRequest = ProjectRequest.builder()
                .name("project1")
                .description("description")
                .build();

        User user = User.builder()
                .id(1L)
                .username("damian")
                .email("damian@gmial.com")
                .projects(projects)
                .build();

        given(userService.findById(1L)).willReturn(Optional.of(user));

        assertThatThrownBy(() -> projectService.createProject(projectRequest, 1L))
                .isInstanceOf(ProjectNameAlreadyExists.class)
                .hasMessageContaining("Project name already exists");

    }


}