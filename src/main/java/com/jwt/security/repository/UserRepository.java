package com.jwt.security.repository;

import com.jwt.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByUsernameAndPassword(String username, String password);

}
