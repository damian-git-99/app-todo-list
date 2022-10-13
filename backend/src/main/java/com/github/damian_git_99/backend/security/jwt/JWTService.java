package com.github.damian_git_99.backend.security.jwt;

import java.util.Map;

public interface JWTService {
    String createToken(String subject, Map<String, Object> payload);

    boolean validateToken(String header);

    Map<String, Object> getClaims(String authorizationHeader);
}
