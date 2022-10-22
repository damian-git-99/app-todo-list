package com.github.damian_git_99.backend.security.filters;

import com.github.damian_git_99.backend.security.jwt.JWTService;
import com.github.damian_git_99.backend.security.jwt.exceptions.InvalidJwtTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationFilterTest {

    @Autowired
    private MockMvc mvc;
    @SpyBean
    JWTService jwtService;

    @Test
    @DisplayName("It should return 403 when token is not sent")
    void shouldFail() throws Exception {
        mvc.perform(post("/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("It should throw an exception when invalid token is sent")
    void shouldFail2() throws Exception {

        assertThatThrownBy(() -> {
            given(jwtService.validateToken("Bearer invalid-token")).willReturn(false);
            mvc.perform(post("/")
                            .header("Authorization", "Bearer invalid-token"))
                    .andExpect(status().isForbidden());
        }).isInstanceOf(InvalidJwtTokenException.class);

    }

    @Test
    @DisplayName("It should throw return 401 when id or authorities don't come in the token")
    void shouldFail3() throws Exception {

        Map<String, Object> payloadToken = new HashMap<>();
        String token = jwtService.createToken(null, payloadToken);

        mvc.perform(post("/")
                        .header("Authorization", "Bearer ".concat(token)))
                .andExpect(status().isUnauthorized());


    }

    @Test
    @DisplayName("It Should not return 401 when token is valid")
    void shouldSuccessWhenTokenIsValid() throws Exception {
        List<String> authorities = List.of("ROLE_USER");
        Map<String, Object> payloadToken = new HashMap<>();
        payloadToken.put("id", 1L);
        payloadToken.put("authorities", authorities);

        String token = jwtService.createToken(null, payloadToken);
        
        mvc.perform(post("/")
                        .header("Authorization", "Bearer ".concat(token)))
                .andExpect(status().isNotFound());
    }

}