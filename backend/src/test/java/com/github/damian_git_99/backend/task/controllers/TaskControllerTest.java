package com.github.damian_git_99.backend.task.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.configs.security.SpringSecurityConfig;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.models.Priority;
import com.github.damian_git_99.backend.task.services.TaskService;
import com.github.damian_git_99.backend.util.BaseControllerTest;
import com.github.damian_git_99.backend.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import(SpringSecurityConfig.class)
class TaskControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TaskService taskService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Nested
    class CreateTaskTests {

        private ResultActions createTaskRequest(TaskRequest request) throws Exception {
            return mvc.perform(post("/api/v1/tasks/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)));
        }

        @Test
        @DisplayName("Should return 400 when name is not sent")
        @WithMockCustomUser
        void should400WhenTaskNameIsNotSent() throws Exception {
            TaskRequest request = TaskRequest.builder()
                    .description("description")
                    .priority(Priority.HIGH)
                    .build();

            createTaskRequest(request)
                    .andExpect(status().isBadRequest());
        }


        @Test
        @DisplayName("Should return 400 when description is not sent")
        @WithMockCustomUser
        void should400WhenTaskDescriptionIsNotSent() throws Exception {
            TaskRequest request = TaskRequest.builder()
                    .taskName("name")
                    .priority(Priority.HIGH)
                    .build();
            createTaskRequest(request)
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when priority is not sent")
        @WithMockCustomUser
        void should400WhenTaskPriorityIsNotSent() throws Exception {
            TaskRequest request = TaskRequest.builder()
                    .taskName("name")
                    .description("description")
                    .build();
            createTaskRequest(request)
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 201 created when all data is sent")
        @WithMockCustomUser
        void shouldReturn201WhenAllDataIsSent() throws Exception {
            TaskRequest request = TaskRequest.builder()
                    .taskName("name")
                    .description("description")
                    .priority(Priority.HIGH)
                    .build();
            createTaskRequest(request)
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Should call createTask when all data is sent")
        @WithMockCustomUser
        void shouldReturnCreateTheTaskWhenAllDataIsSent() throws Exception {
            TaskRequest request = TaskRequest.builder()
                    .taskName("name")
                    .description("description")
                    .priority(Priority.HIGH)
                    .build();
            createTaskRequest(request);
            then(taskService)
                    .should(atLeastOnce())
                    .createTask(any(AuthenticatedUser.class), any(Long.class), any(TaskRequest.class));
        }

        @Test
        @DisplayName("Should Throw IllegalArgumentException when property is invalid")
        @WithMockCustomUser
        void shouldThrow() throws Exception {
            assertThatThrownBy(() -> {
                TaskRequest request = TaskRequest.builder()
                        .taskName("name")
                        .description("description")
                        .priority(Priority.valueOf("Invalid_Priority"))
                        .build();
                createTaskRequest(request)
                        .andExpect(status().isCreated());
            }).isInstanceOf(IllegalArgumentException.class);

        }

    }

    @Nested
    class DeleteTaskTests {

        @Test
        @DisplayName("Should Return 200 ok when the task was deleted")
        @WithMockCustomUser
        void shouldReturn200WhenTaskWasDeleted() throws Exception {
            mvc.perform(delete("/api/v1/tasks/1/1"))
                    .andExpect(status().isOk());

            then(taskService)
                    .should(atLeastOnce())
                    .deleteTaskById(any(AuthenticatedUser.class), any(Long.class), any(Long.class));
        }

    }

}