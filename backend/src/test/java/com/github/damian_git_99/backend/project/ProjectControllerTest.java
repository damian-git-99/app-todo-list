package com.github.damian_git_99.backend.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damian_git_99.backend.project.ProjectController;
import com.github.damian_git_99.backend.project.dto.ProjectRequest;
import com.github.damian_git_99.backend.project.services.ProjectService;
import com.github.damian_git_99.backend.util.BaseControllerTest;
import com.github.damian_git_99.backend.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProjectService projectService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("It should return return 400 when name is not sent")
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
    @DisplayName("It should return return 400 when description is not sent")
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
    @DisplayName("It should return return 200 when all data sent is valid")
    @WithMockCustomUser(id = 1L)
    void shouldSuccess() throws Exception {
        ProjectRequest request = new ProjectRequest();
        request.setName("project");
        request.setDescription("my project description");
        mvc.perform(post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

}