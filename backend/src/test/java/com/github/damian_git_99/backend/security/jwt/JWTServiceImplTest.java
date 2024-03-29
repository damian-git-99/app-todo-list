package com.github.damian_git_99.backend.security.jwt;

import com.github.damian_git_99.backend.configs.security.jwt.JWTService;
import com.github.damian_git_99.backend.configs.security.jwt.JWTServiceImpl;
import com.github.damian_git_99.backend.configs.security.jwt.exceptions.InvalidJwtTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JWTServiceImplTest {

    private final JWTService jwtService = new JWTServiceImpl();

    @Test
    @DisplayName("It Should create a token")
    void shouldCreateAccessToken() {
        String token = jwtService.createToken("damian@gmail.com", new HashMap<>());
        assertThat(token).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("It Should validate a token correctly")
    void shouldCreateToken(){
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 2);

        String token = "Bearer " + jwtService.createToken("damian@gmail.com", payload);
        boolean result = jwtService.validateToken(token);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("It should fail trying to validate a token")
    void shouldFailValidateToken() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 2);

        String finalToken = "Bearer " + jwtService.createToken("damian@gmail.com", payload) + "1"; // concatenate a character to make it an invalid token

        Exception exception = assertThrows(InvalidJwtTokenException.class, () -> {
            boolean result = jwtService.validateToken(finalToken);
        });

        assertThat(exception.getClass()).isEqualTo(InvalidJwtTokenException.class);
        assertThat(exception.getMessage()).isEqualTo("Expired or invalid JWT token");
    }

}