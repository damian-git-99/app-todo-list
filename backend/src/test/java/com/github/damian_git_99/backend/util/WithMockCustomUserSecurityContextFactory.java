package com.github.damian_git_99.backend.util;

import com.github.damian_git_99.backend.security.AuthenticatedUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Collection<? extends GrantedAuthority> roles = Arrays.stream(annotation.roles())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        var auth = new AuthenticatedUser(annotation.id());
        var token = new UsernamePasswordAuthenticationToken(auth, annotation.password(), roles);
        context.setAuthentication(token);
        return context;
    }

}
