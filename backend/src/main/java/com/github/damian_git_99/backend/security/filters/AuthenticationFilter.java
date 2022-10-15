package com.github.damian_git_99.backend.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damian_git_99.backend.security.exceptions.BadCredentialsException;
import com.github.damian_git_99.backend.security.filters.dto.UserRequestAuth;
import com.github.damian_git_99.backend.security.jwt.JWTService;
import com.github.damian_git_99.backend.user.entities.User;
import com.github.damian_git_99.backend.user.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.validation.ObjectError;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v1/auth", HttpMethod.POST.name()));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserRequestAuth requestAuth = null;

        try {
            requestAuth = objectMapper.readValue(request.getInputStream(), UserRequestAuth.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var email = requestAuth.getEmail();
        var password = requestAuth.getPassword();

        if (email == null || password == null) {
            // todo throw error in body
            Map<String, Object> body = new HashMap<>();
            body.put("error", "Email and password cannot be empty");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(objectMapper.writeValueAsString(body));
            return null;
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = userRepository.findByEmail(authResult.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", user.getId());
        String token = jwtService.createToken(null, payload);

        response.setHeader("Authorization", "Bearer ".concat(token));
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);

        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Authentication ERROR: incorrect email or password");
        response.getWriter().write(objectMapper.writeValueAsString(map));
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
    }

}
