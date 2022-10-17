package com.github.damian_git_99.backend.user.services;

import com.github.damian_git_99.backend.exceptions.InternalServerException;
import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.entities.User;
import com.github.damian_git_99.backend.user.exceptions.EmailAlreadyTakenException;
import com.github.damian_git_99.backend.user.repositories.UserRepository;
import com.github.damian_git_99.backend.user.role.Role;
import com.github.damian_git_99.backend.user.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public void signUp(UserRequest userRequest) {
        Optional<User> userExists = userRepository.findByEmail(userRequest.getEmail());

        if (userExists.isPresent()) {
            log.info("Email is already Taken: " + userRequest.getEmail());
            throw new EmailAlreadyTakenException("Email is already Taken");
        }

        Role role = roleService.findRoleByName("ROLE_USER")
                .orElseThrow(() -> {
                    log.error("ERROR: Role USER not found");
                    return new InternalServerException("An error occurred on the server");
                });

        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());

        User user = User.builder()
                .password(hashedPassword)
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .build();

        user.addRole(role);
        log.info("saving new user in the db");
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found");
        User user = optionalUser.get();
        System.out.println(user.getRoles());
        Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
