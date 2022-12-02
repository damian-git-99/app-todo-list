package com.github.damian_git_99.backend.task.services;

import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import com.github.damian_git_99.backend.project.models.Project;
import com.github.damian_git_99.backend.project.services.ProjectService;
import com.github.damian_git_99.backend.task.daos.TaskDao;
import com.github.damian_git_99.backend.task.dto.TaskRequest;
import com.github.damian_git_99.backend.task.exceptions.ForbiddenTaskException;
import com.github.damian_git_99.backend.task.exceptions.TaskNotFoundException;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private ProjectService projectService;
    @Mock
    private TaskDao taskDao;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Nested
    class CreateTaskTests {

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

    @Nested
    class DeleteTaskByIdTests {

        @Test
        @DisplayName("Should throw TaskNotFoundException when task is not found")
        void shouldReturnThrowExceptionWhenTaskIsNotFound() {
            AuthenticatedUser user = new AuthenticatedUser(1L);
            Project project = Project.builder()
                    .id(1L)
                    .name("project")
                    .description("my new project")
                    .build();

            given(projectService.findProjectById(1L, user))
                    .willReturn(project);

            given(taskDao.findById(1L)).willReturn(Optional.empty());

            assertThatThrownBy(() -> {
                taskService.deleteTaskById(user, 1L, 1L);
            }).isInstanceOf(TaskNotFoundException.class);

            then(taskDao).should(never()).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("Should return ForbiddenTaskException when the task does not belong to the project")
        void shouldThrowForbiddenTaskException() {
            AuthenticatedUser user = new AuthenticatedUser(1L);
            Project project1 = Project.builder()
                    .id(2L)
                    .build();

            Project project2 = Project.builder()
                    .id(1L)
                    .build();

            Task task = Task.builder()
                    .taskName("new task")
                    .description("description...")
                    .priority(Priority.HIGH)
                    .project(project2)
                    .build();

            given(projectService.findProjectById(2L, user))
                    .willReturn(project1);

            given(taskDao.findById(1L)).willReturn(Optional.of(task));

            assertThatThrownBy(() -> {
                taskService.deleteTaskById(user, 2L, 1L);
            }).isInstanceOf(ForbiddenTaskException.class)
                    .hasMessage("Forbidden operation");

            then(taskDao).should(never()).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("should Delete The Task")
        void shouldDeleteTheTask(){
            AuthenticatedUser user = new AuthenticatedUser(1L);
            Project project = Project.builder()
                    .id(1L)
                    .build();

            Task task = Task.builder()
                    .taskName("new task")
                    .description("description...")
                    .priority(Priority.HIGH)
                    .project(project)
                    .build();

            given(projectService.findProjectById(1L, user))
                    .willReturn(project);

            given(taskDao.findById(1L)).willReturn(Optional.of(task));


            taskService.deleteTaskById(user, 1L, 1L);


            then(taskDao).should(atLeastOnce()).deleteById(any(Long.class));
        }
    }

}