package com.github.damian_git_99.backend.user.dto;

import com.github.damian_git_99.backend.user.models.User;
import com.github.damian_git_99.backend.utils.AbstractConverter;

public class UserResponseConverter implements AbstractConverter <User, UserResponse> {
    @Override
    public User toEntity(UserResponse dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }

    @Override
    public UserResponse toDto(User entity) {
        return UserResponse.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }
}
