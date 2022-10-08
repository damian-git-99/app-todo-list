package com.github.damian_git_99.backend.user.services;

import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.entities.User;
import com.github.damian_git_99.backend.user.exceptions.EmailAlreadyTakenException;
import com.github.damian_git_99.backend.user.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(UserRequest userRequest) {
        Optional<User> userExists = userRepository.findByEmail(userRequest.getEmail());

        if (userExists.isPresent()) {
            log.info("Email is already Taken: " + userRequest.getEmail());
            throw new EmailAlreadyTakenException("Email is already Taken");
        }

        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());

        User user = User.builder()
                .password(hashedPassword)
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .build();

        log.info("saving new user in the db");
        userRepository.save(user);
    }

}
