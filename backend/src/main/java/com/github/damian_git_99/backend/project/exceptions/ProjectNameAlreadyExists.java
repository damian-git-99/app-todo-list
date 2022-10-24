package com.github.damian_git_99.backend.project.exceptions;

public class ProjectNameAlreadyExists extends RuntimeException {
    public ProjectNameAlreadyExists(String message) {
        super(message);
    }
}
