package com.github.damian_git_99.backend.user.services;

import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.models.User;

import java.util.Optional;

public interface UserService {

    void signUp(UserRequest userRequest);
    Optional<User> findById(Long id);

}
