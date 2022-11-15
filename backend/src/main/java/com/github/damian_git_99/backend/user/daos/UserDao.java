package com.github.damian_git_99.backend.user.daos;

import com.github.damian_git_99.backend.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    @Query("SELECT u From User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

}
