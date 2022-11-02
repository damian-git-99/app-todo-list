package com.github.damian_git_99.backend.utils;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class BindingResultUtils {

    private BindingResultUtils() {}

    public static Map<String, Object> getFirstError(BindingResult result) {
        var firstError = result.getFieldErrors()
                .stream()
                .findFirst()
                .orElseThrow();

        Map<String, Object> response = new HashMap<>();
        response.put("error", firstError.getDefaultMessage());
        return response;
    }

}
