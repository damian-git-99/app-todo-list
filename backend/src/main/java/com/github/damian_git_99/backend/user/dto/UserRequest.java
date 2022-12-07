package com.github.damian_git_99.backend.user.dto;

import com.github.damian_git_99.backend.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * A DTO for the {@link User} entity
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "username cannot be empty")
    private String username;
    @NotBlank(message = "email cannot be empty")
    @Email(message = "must be a well-formed email address")
    private String email;
    @NotBlank(message = "password cannot be empty")
    private String password;

}
