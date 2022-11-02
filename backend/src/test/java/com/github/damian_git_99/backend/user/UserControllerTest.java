package com.github.damian_git_99.backend.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damian_git_99.backend.util.BaseControllerTest;
import com.github.damian_git_99.backend.util.WithMockCustomUser;
import com.github.damian_git_99.backend.security.jwt.JWTService;
import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.entities.User;
import com.github.damian_git_99.backend.user.repositories.UserRepository;
import com.github.damian_git_99.backend.user.role.RoleRepository;
import com.github.damian_git_99.backend.user.services.UserService;
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

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atMostOnce;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mvc;

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

    @Nested
    class getUserDetailsTests {

        @Test
        @DisplayName("It Should return 403 when user is not authorized")
        void shouldFailWhenTokenIsNotSent() throws Exception {
            mvc.perform(get("/api/v1/users/info"))
                    .andExpect(status().isForbidden());

        }

        @Test
        @DisplayName("It Should return 200 ok when user is authorized")
        @WithMockCustomUser(id = 1)
        void shouldSuccess() throws Exception {
            User user = User.builder()
                            .username("damian")
                            .email("damian@gmail.com")
                            .build();

            given(userService.findById(1L)).willReturn(Optional.of(user));

            mvc.perform(get("/api/v1/users/info"))
                    .andExpect(status().isOk());

        }


    }


}