package com.github.damian_git_99.backend.security.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damian_git_99.backend.security.filters.dto.UserRequestAuth;
import com.github.damian_git_99.backend.security.jwt.JWTService;
import com.github.damian_git_99.backend.user.UserController;
import com.github.damian_git_99.backend.user.entities.User;
import com.github.damian_git_99.backend.user.repositories.UserRepository;
import com.github.damian_git_99.backend.user.role.Role;
import com.github.damian_git_99.backend.user.role.RoleRepository;
import com.github.damian_git_99.backend.user.role.RoleService;
import com.github.damian_git_99.backend.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationFilterTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    JWTService jwtService;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    UserRepository userRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should return 400 when email is not sent")
    void shouldFailEmail() throws Exception {

        UserRequestAuth auth = new UserRequestAuth();
        auth.setPassword("1234");

        mvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(auth)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when password is not sent")
    void shouldFailPassword() throws Exception {

        UserRequestAuth auth = new UserRequestAuth();
        auth.setEmail("damian@gmail.com");

        mvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(auth)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 400 if credentials are incorrect")
    void shouldFail() throws Exception {
        var roles = List.of(new Role("USER"));
        User user = new User();
        user.setEmail("damian@gmail.com");
        user.setPassword("1234");
        user.setRoles(roles);

        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));
        given(passwordEncoder.matches("1234", "12345")).willReturn(false);

        UserRequestAuth auth = new UserRequestAuth("damian@gmail.com", "12345");

        mvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(auth)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Authentication ERROR: incorrect email or password"));
    }

    @Test
    @DisplayName("Should return 200 when credentials are correct")
    void shouldSuccess() throws Exception {

        var roles = List.of(new Role("USER"));
        User user = new User();
        user.setEmail("damian@gmail.com");
        user.setPassword("1234");
        user.setRoles(roles);

        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));
        given(passwordEncoder.matches("1234", "1234")).willReturn(true);
        given(jwtService.createToken(any(), any())).willReturn("generated token");

        UserRequestAuth auth = new UserRequestAuth("damian@gmail.com", "1234");

        mvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(auth)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return a token in the headers and body when credentials are correct")
    void shouldSuccess2() throws Exception {

        var roles = List.of(new Role("USER"));
        User user = new User();
        user.setEmail("damian@gmail.com");
        user.setPassword("1234");
        user.setRoles(roles);

        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));
        given(passwordEncoder.matches("1234", "1234")).willReturn(true);
        given(jwtService.createToken(any(), any())).willReturn("generated token");

        UserRequestAuth auth = new UserRequestAuth("damian@gmail.com", "1234");

        mvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(auth)))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(header().string("Authorization", "Bearer generated token"));
    }

}