package com.github.damian_git_99.backend.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler(ProjectNameAlreadyExists.class)
    ResponseEntity<Map<String, Object>> handleEmailAlreadyTaken(ProjectNameAlreadyExists exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(map);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    ResponseEntity<Map<String, Object>> handleProjectNotFound(ProjectNotFoundException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(map);
    }

    @ExceptionHandler(ForbiddenProjectException.class)
    ResponseEntity<Map<String, Object>> handleForbiddenProject(ForbiddenProjectException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(map);
    }

}
