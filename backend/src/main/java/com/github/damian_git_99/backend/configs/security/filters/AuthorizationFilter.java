package com.github.damian_git_99.backend.configs.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.damian_git_99.backend.configs.security.jwt.JWTService;
import com.github.damian_git_99.backend.configs.security.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final ObjectMapper mapper;

    public AuthorizationFilter(JWTService jwtService) {
        this.jwtService = jwtService;
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtService.validateToken(token)) {
                Claims claims = (Claims) jwtService.getClaims(token);
                Integer id = (Integer) claims.get("id");
                List<String> list = (List<String>) claims.get("authorities", List.class);
                Collection<? extends GrantedAuthority> authorities = list.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                var user = new AuthenticatedUser(id.longValue());
                var credentials = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(credentials);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            Map<String, Object> errors = new HashMap<>();
            errors.put("message", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getWriter(), errors);
        }

    }

}
