package com.github.damian_git_99.backend.user.services;

import com.github.damian_git_99.backend.utils.exceptions.InternalServerException;
import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.models.User;
import com.github.damian_git_99.backend.user.exceptions.EmailAlreadyTakenException;
import com.github.damian_git_99.backend.user.daos.UserDao;
import com.github.damian_git_99.backend.user.role.Role;
import com.github.damian_git_99.backend.user.role.RoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserDao userDao;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    class SignUpTests {
        @Test
        @DisplayName("Should throw EmailAlreadyTakenException when email is already taken")
        void shouldThrowEmailAlreadyTakenException() {
            User user = User.builder()
                    .username("damian")
                    .email("damian@gmail.com")
                    .password("123456").build();

            UserRequest userRequest = UserRequest.builder()
                    .username("Irving")
                    .email("damian@gmail.com")
                    .password("123456").build();

            given(userDao.findByEmail("damian@gmail.com"))
                    .willReturn(Optional.of(user));

            assertThatThrownBy(() -> userService.signUp(userRequest))
                    .isInstanceOf(EmailAlreadyTakenException.class)
                    .hasMessageContaining("Email is already Taken");

            then(passwordEncoder).should(never()).encode(anyString());
            then(userDao).should(never()).save(any());
        }

        @Test
        @DisplayName("Should throw InternalServerErrorException when role USER is not found")
        void shouldThrowInternalServerErrorException() {
            UserRequest userRequest = UserRequest.builder()
                    .username("Irving")
                    .email("damian@gmail.com")
                    .password("123456").build();
            
            given(userDao.findByEmail("damian@gmail.com"))
                    .willReturn(Optional.empty());
            assertThatThrownBy(() -> userService.signUp(userRequest))
                    .isInstanceOf(InternalServerException.class)
                    .hasMessageContaining("An error occurred on the server");
        }

        @Test
        @DisplayName("Should hash the password")
        void shouldHashThePassword() {
            UserRequest userRequest = UserRequest.builder()
                    .username("Irving")
                    .email("damian@gmail.com")
                    .password("123456").build();
            Role role = new Role("USER");

            given(userDao.findByEmail("damian@gmail.com"))
                    .willReturn(Optional.empty());
            given(passwordEncoder.encode("123456")).willReturn("hashed password");
            given(roleService.findRoleByName("ROLE_USER")).willReturn(Optional.of(role));

            userService.signUp(userRequest);
            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

            verify(userDao).save(captor.capture());
            then(passwordEncoder).should(atMostOnce()).encode("123456");
            assertThat(captor.getValue().getPassword()).isEqualTo("hashed password");
        }

        @Test
        @DisplayName("Should call userDao")
        void shouldCallUserDao() {
            UserRequest userRequest = UserRequest.builder()
                    .username("Irving")
                    .email("damian@gmail.com")
                    .password("123456").build();
            Role role = new Role("USER");

            given(userDao.findByEmail("damian@gmail.com"))
                    .willReturn(Optional.empty());
            given(passwordEncoder.encode("123456")).willReturn("hashed password");
            given(roleService.findRoleByName("ROLE_USER")).willReturn(Optional.of(role));

            userService.signUp(userRequest);

            then(userDao).should(atMostOnce()).save(any());

        }
    }

}