package com.github.damian_git_99.backend.user.dto;

import com.github.damian_git_99.backend.user.models.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * A DTO for the {@link User} entity
 */
@Builder
@Data
public class UserRequest {

    @NotBlank(message = "username cannot be empty")
    private String username;
    @NotBlank(message = "email cannot be empty")
    @Email(message = "must be a well-formed email address")
    private String email;
    @NotBlank(message = "password cannot be empty")
    private String password;

}
