package com.github.damian_git_99.backend.project.services;

import com.github.damian_git_99.backend.project.LoadData;
import com.github.damian_git_99.backend.project.Project;
import com.github.damian_git_99.backend.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.project.exceptions.ForbiddenProjectException;
import com.github.damian_git_99.backend.project.exceptions.ProjectNameAlreadyExists;
import com.github.damian_git_99.backend.project.exceptions.ProjectNotFoundException;
import com.github.damian_git_99.backend.project.daos.ProjectDao;
import com.github.damian_git_99.backend.security.AuthenticatedUser;
import com.github.damian_git_99.backend.user.entities.User;
import com.github.damian_git_99.backend.user.exceptions.UserNotFoundException;
import com.github.damian_git_99.backend.user.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {


    @Mock
    private UserService userService;
    @Mock
    ProjectDao projectDao;
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
    void shouldThrowProjectNameAlreadyExists() {
        Project project = new Project("project1", "description of my project");
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

    @Test
    @DisplayName("Should create a project")
    void shouldCreateProject() {
        Project project = new Project("project1", "description of my project");
        List<Project> projects = List.of(project);
        ProjectRequest projectRequest = ProjectRequest.builder()
                .name("project2")
                .description("description")
                .build();

        User user = User.builder()
                .id(1L)
                .username("damian")
                .email("damian@gmial.com")
                .projects(projects)
                .build();

        given(userService.findById(1L)).willReturn(Optional.of(user));

        projectService.createProject(projectRequest, 1L);

        then(projectDao).should(atMostOnce()).save(any());
    }

    @Nested
    class findProjectByIdTests {

        @Test
        @DisplayName("Should Throw Project Not Found when project does not exist")
        void shouldThrowProjectNotFound() {
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(1L);

            given(projectDao.findById(1L)).willReturn(Optional.empty());

            assertThatThrownBy(() -> projectService.findProjectById(1L, authenticatedUser))
                    .isInstanceOf(ProjectNotFoundException.class);
            then(projectDao).should(atLeastOnce()).findById(1L);
        }

        @Test
        @DisplayName("Should Throw Forbidden Project Exception when project does not belong to the authenticated user")
        void shouldThrowForbiddenProjectException() {
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(2L);
            Project project = LoadData.LoadProject();
            project.setId(1L);

            User user = User.builder()
                    .id(1L)
                    .username("damian@gmail.com")
                    .build();

            project.setUser(user);

            given(projectDao.findById(1L)).willReturn(Optional.of(project));

            assertThatThrownBy(() -> projectService.findProjectById(1L, authenticatedUser))
                    .isInstanceOf(ForbiddenProjectException.class);

            then(projectDao).should(atLeastOnce()).findById(1L);
        }

        @Test
        @DisplayName("Should return project when project belong to the authenticated user")
        void shouldReturnProject() {
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(1L);
            Project project = LoadData.LoadProject();
            project.setId(1L);

            User user = User.builder()
                    .id(1L)
                    .username("damian@gmail.com")
                    .build();

            project.setUser(user);

            given(projectDao.findById(1L)).willReturn(Optional.of(project));

            Project project1 = projectService.findProjectById(1L, authenticatedUser);

            assertThat(project1).isNotNull();
        }

    }


}