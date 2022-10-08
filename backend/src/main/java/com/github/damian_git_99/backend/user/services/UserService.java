package com.github.damian_git_99.backend.user.services;

import com.github.damian_git_99.backend.user.dto.UserRequest;

public interface UserService {

    void signUp(UserRequest userRequest);

}
