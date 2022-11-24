package com.github.damian_git_99.backend.task.daos;

import com.github.damian_git_99.backend.task.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDao extends JpaRepository<Task, Long> {
}
