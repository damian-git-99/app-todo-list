package com.github.damian_git_99.backend.task.exceptions;

public class ForbiddenTaskException extends RuntimeException {
    public ForbiddenTaskException(String message) {
        super(message);
    }
}
