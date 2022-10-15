package com.github.damian_git_99.backend.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damian_git_99.backend.security.jwt.JWTService;
import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.repositories.UserRepository;
import com.github.damian_git_99.backend.user.role.RoleRepository;
import com.github.damian_git_99.backend.user.role.RoleService;
import com.github.damian_git_99.backend.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private JWTService jwtService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Nested
    class SignUpTests {

        @Mock
        private UserService userService;


        private ResultActions requestSignUp(UserRequest userRequest) throws Exception {
            return mvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(userRequest)));
        }

        @Test
        @DisplayName("Should return 400 when username is not sent")
        void shouldFailWhenUsernameIsNotSent() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .email("damian@gmail.com")
                    .password("123456")
                    .build();

            requestSignUp(userRequest)
                    .andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("Should return username cannot be empty when username is not sent")
        void shouldFailWhenUsernameIsNotSent2() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .email("damian@gmail.com")
                    .password("123456")
                    .build();

            requestSignUp(userRequest)
                    .andExpect(jsonPath("$.error").value("username cannot be empty"));

        }

        @Test
        @DisplayName("Should return 400 when email is not sent")
        void shouldFailWhenEmailIsNotSent() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .username("damian")
                    .password("123456").build();

            requestSignUp(userRequest)
                    .andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("Should return email cannot be empty when username is not sent")
        void shouldFailWhenEmailIsNotSent2() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .username("damian")
                    .password("123456").build();

            requestSignUp(userRequest)
                    .andExpect(jsonPath("$.error").value("email cannot be empty"));

        }

        @Test
        @DisplayName("Should return must be a well-formed email address when email is sent but is not valid")
        void shouldFailWhenEmailIsNotValid() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .username("damian")
                    .email("damian888")
                    .password("123456").build();

            requestSignUp(userRequest)
                    .andExpect(jsonPath("$.error").value("must be a well-formed email address"));

        }

        @Test
        @DisplayName("Should return 400 when password is not sent")
        void shouldFailWhePasswordIsNotSent() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .username("damian")
                    .email("damian888")
                    .build();

            requestSignUp(userRequest)
                    .andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("Should return password cannot be empty when password is not sent")
        void shouldFailWhePasswordIsNotSent2() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .username("damian")
                    .email("damian@gmail.com")
                    .build();

            requestSignUp(userRequest)
                    .andExpect(jsonPath("$.error").value("password cannot be empty"));

        }


        @Test
        @DisplayName("Should return 201 when all user info is sent and is valid")
        void shouldSuccess() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .username("damian")
                    .email("damian@gmail.com")
                    .password("123456")
                    .build();

            requestSignUp(userRequest)
                    .andExpect(status().isCreated());

        }

        @Test
        @DisplayName("Should call userService when all user info is sent and is valid")
        void shouldSuccess2() throws Exception {
            UserRequest userRequest = UserRequest.builder()
                    .username("damian")
                    .email("damian@gmail.com")
                    .password("123456")
                    .build();

            requestSignUp(userRequest);

            then(userService).should(atMostOnce()).signUp(userRequest);
        }


    }


}