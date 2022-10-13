package com.github.damian_git_99.backend.security.exceptions;

import com.github.damian_git_99.backend.user.exceptions.EmailAlreadyTakenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<Map<String, Object>> handleException(BadCredentialsException exception){
        Map<String, Object> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(map);
    }

}
