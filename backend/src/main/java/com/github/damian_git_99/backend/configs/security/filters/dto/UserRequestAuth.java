package com.github.damian_git_99.backend.configs.security.filters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestAuth {

    private String email;
    private String password;


}
