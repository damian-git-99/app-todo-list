package com.github.damian_git_99.backend.project.daos;

import com.github.damian_git_99.backend.project.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project, Long> {
}
