package com.jwt.security.service;

import com.jwt.security.model.User;
import com.jwt.security.request.UserCreateRequest;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByUsernameAndPassword(String username, String password);

    Optional<User> createUser(UserCreateRequest userCreateRequest);

}
