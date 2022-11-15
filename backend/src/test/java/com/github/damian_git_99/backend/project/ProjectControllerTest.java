package com.github.damian_git_99.backend.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damian_git_99.backend.project.controllers.ProjectController;
import com.github.damian_git_99.backend.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.project.services.ProjectService;
import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.user.models.User;
import com.github.damian_git_99.backend.util.BaseControllerTest;
import com.github.damian_git_99.backend.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProjectService projectService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should return return 400 when name is not sent")
    @WithMockCustomUser(id = 1L)
    void shouldFailWhenNameIsNotSent() throws Exception {
        ProjectRequest request = new ProjectRequest();
        request.setDescription("my project description");
        mvc.perform(post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return return 400 when description is not sent")
    @WithMockCustomUser(id = 1L)
    void shouldFailWhenDescriptionIsNotSent() throws Exception {
        ProjectRequest request = new ProjectRequest();
        request.setName("project");
        mvc.perform(post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return return 200 when all data sent is valid")
    @WithMockCustomUser(id = 1L)
    void shouldReturn200WhenAllDataIsValid() throws Exception {
        ProjectRequest request = new ProjectRequest();
        request.setName("project");
        request.setDescription("my project description");
        mvc.perform(post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    @Nested
    class FindProjectByUserTests {

        @Test
        @DisplayName("Should return 404 when user does not exist")
        @WithMockCustomUser(id = 1L)
        void shouldReturn404WhenUserDoesNotExist() throws Exception {
            mvc.perform(get("/api/v1/projects")
            ).andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 200 when user exists")
        @WithMockCustomUser(id = 1L)
        void shouldReturn200WhenUserExists() throws Exception {
            User user = User.builder()
                    .username("damian")
                    .email("damian@gmail.com")
                    .build();
            given(userService.findById(1L)).willReturn(Optional.of(user));
            mvc.perform(get("/api/v1/projects")
            ).andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return a list of projects when user exists")
        @WithMockCustomUser(id = 1L)
        void shouldReturnAListOfProjectsWhenUserExists() throws Exception {

            List<Project> projects = LoadData.loadProjects();

            User user = User.builder()
                    .username("damian")
                    .email("damian@gmail.com")
                    .projects(projects)
                    .build();

            given(userService.findById(1L)).willReturn(Optional.of(user));

            mvc.perform(get("/api/v1/projects")
                    ).andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
        }

    }

    @Nested
    class FindProjectByIdTests {

        @Test
        @DisplayName("Should return 200 ok when project is found successfully")
        @WithMockCustomUser(id = 1L)
        void shouldReturn200WhenProjectIsFound() throws Exception {

            given(projectService.findProjectById(any(Long.class), any(AuthenticatedUser.class)))
                    .willReturn(LoadData.LoadProject());

            mvc.perform(get("/api/v1/projects/1"))
                    .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Should return a project when project is found successfully")
        @WithMockCustomUser(id = 1L)
        void shouldReturnProject() throws Exception {
            given(projectService.findProjectById(any(Long.class), any(AuthenticatedUser.class)))
                    .willReturn(LoadData.LoadProject());

            mvc.perform(get("/api/v1/projects/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").exists())
                    .andExpect(jsonPath("$.description").exists())
                    .andExpect(jsonPath("$.createdAt").exists());
        }


    }

    @Nested
    class DeleteProjectByIdTests {

        @Test
        @DisplayName("Should return 200 ok when project is deleted successfully")
        @WithMockCustomUser(id = 1L)
        void shouldReturnProject() throws Exception {
            doNothing()
                    .when(projectService)
                    .deleteProjectById(isA(Long.class), isA(AuthenticatedUser.class));

            mvc.perform(delete("/api/v1/projects/1"))
                    .andExpect(status().isOk());
        }


    }

}