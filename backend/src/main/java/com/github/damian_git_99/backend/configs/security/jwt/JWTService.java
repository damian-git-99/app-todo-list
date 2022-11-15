package com.github.damian_git_99.backend.configs.security.jwt;

import com.github.damian_git_99.backend.configs.security.jwt.exceptions.InvalidJwtTokenException;

import java.util.Map;

public interface JWTService {
    String createToken(String subject, Map<String, Object> payload);

    boolean validateToken(String header) throws InvalidJwtTokenException;

    Map<String, Object> getClaims(String authorizationHeader);
}
