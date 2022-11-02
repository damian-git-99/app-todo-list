package com.github.damian_git_99.backend.util;

import com.github.damian_git_99.backend.security.jwt.JWTService;
import com.github.damian_git_99.backend.user.repositories.UserRepository;
import com.github.damian_git_99.backend.user.role.RoleRepository;
import com.github.damian_git_99.backend.user.services.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BaseControllerTest {

    @MockBean
    protected RoleRepository roleRepository;
    @MockBean
    protected JWTService jwtService;
    @MockBean
    protected PasswordEncoder passwordEncoder;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected UserService userService;

}
