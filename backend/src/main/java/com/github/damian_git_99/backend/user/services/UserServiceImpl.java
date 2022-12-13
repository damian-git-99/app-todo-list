package com.github.damian_git_99.backend.user.services;

import com.github.damian_git_99.backend.user.dto.UserUpdateRequest;
import com.github.damian_git_99.backend.user.exceptions.UserNotFoundException;
import com.github.damian_git_99.backend.utils.exceptions.InternalServerException;
import com.github.damian_git_99.backend.user.dto.UserRequest;
import com.github.damian_git_99.backend.user.models.User;
import com.github.damian_git_99.backend.user.exceptions.EmailAlreadyTakenException;
import com.github.damian_git_99.backend.user.daos.UserDao;
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
    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder
            , UserDao userDao
            , RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    public void signUp(UserRequest userRequest) {
        Optional<User> userExists = userDao.findByEmail(userRequest.getEmail());
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
        userDao.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User updateUser(Long userId, UserUpdateRequest request) {
        User user = this.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        String email = request.getEmail() != null ? request.getEmail() : user.getEmail();
        String username = request.getUsername() != null ? request.getUsername() : user.getUsername();
        user.setEmail(email);
        user.setUsername(username);
        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = this.userDao.findByEmail(email);
        User user = optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
