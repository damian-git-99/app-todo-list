package com.github.damian_git_99.backend.user.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(EmailAlreadyTakenException.class)
    ResponseEntity<Map<String, Object>> handleEmailAlreadyTaken(EmailAlreadyTakenException exception){
        Map<String, Object> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(map);
    }

}
