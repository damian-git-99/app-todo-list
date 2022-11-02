package com.github.damian_git_99.backend.project.repositories;

import com.github.damian_git_99.backend.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
