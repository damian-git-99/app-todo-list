package com.github.damian_git_99.backend.security.jwt.exceptions;

public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
