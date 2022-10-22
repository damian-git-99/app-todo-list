package com.github.damian_git_99.backend.util;

import com.github.damian_git_99.backend.security.jwt.JWTService;
import com.github.damian_git_99.backend.user.repositories.UserRepository;
import com.github.damian_git_99.backend.user.role.RoleRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BaseControllerTest {

    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private JWTService jwtService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;

}
