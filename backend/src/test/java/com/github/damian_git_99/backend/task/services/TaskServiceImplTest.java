package com.github.damian_git_99.backend.task.services;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.project.services.ProjectService;
import com.github.damian_git_99.backend.task.daos.TaskDao;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.models.Priority;
import com.github.damian_git_99.backend.task.models.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private ProjectService projectService;
    @Mock
    private TaskDao taskDao;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Nested
    class createTaskTests {

        @DisplayName("Should create a task when project is found")
        @Test
        void shouldCreateATask() {
            AuthenticatedUser user = new AuthenticatedUser(1L);
            TaskRequest taskRequest = new TaskRequest("task", "new task", Priority.HIGH);

            Project project = Project.builder()
                    .id(1L)
                    .name("project")
                    .description("my new project")
                    .build();

            given(projectService.findProjectById(1L, user))
                    .willReturn(project);

            taskService.createTask(user, 1L, taskRequest);

            then(taskDao).should(atLeastOnce()).save(any(Task.class));
        }

        @DisplayName("Should return a task that has a project relationship")
        @Test
        void projectShouldBelongToTask() {
            AuthenticatedUser user = new AuthenticatedUser(1L);
            TaskRequest taskRequest = new TaskRequest("task", "new task", Priority.HIGH);

            Project project = Project.builder()
                    .id(1L)
                    .name("project")
                    .description("my new project")
                    .build();

            given(projectService.findProjectById(1L, user))
                    .willReturn(project);

            given(taskDao.save(any(Task.class)))
                    .willAnswer(new Answer<Task>() {
                        @Override
                        public Task answer(InvocationOnMock invocationOnMock) throws Throwable {
                            Task task = invocationOnMock.getArgument(0);
                            task.setId(1L);
                            return task;
                        }
                    });

            Task task = taskService.createTask(user, 1L, taskRequest);
            assertThat(task.getProject()).isEqualTo(project);
        }


    }

}