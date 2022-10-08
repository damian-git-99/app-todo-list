package com.github.damian_git_99.backend.user.exceptions;

public class EmailAlreadyTakenException extends RuntimeException{
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
