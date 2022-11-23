package com.github.damian_git_99.backend.configs.security;

import com.github.damian_git_99.backend.configs.security.filters.AuthenticationFilter;
import com.github.damian_git_99.backend.configs.security.jwt.JWTService;
import com.github.damian_git_99.backend.user.daos.UserDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomHttpConfigurer extends AbstractHttpConfigurer<CustomHttpConfigurer, HttpSecurity> {

    private final JWTService jwtService;
    private final UserDao userDao;

    public CustomHttpConfigurer(JWTService jwtService, UserDao userDao) {
        this.jwtService = jwtService;
        this.userDao = userDao;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager =
                http.getSharedObject(AuthenticationManager.class);
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, jwtService, userDao);
        http.addFilter(filter);
    }

}
