package com.github.damian_git_99.backend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalServerException.class)
    ResponseEntity<Map<String, Object>> handleInternalError(InternalServerException exception){
        Map<String, Object> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(map);
    }

}
