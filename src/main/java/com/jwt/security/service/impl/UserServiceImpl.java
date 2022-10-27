package com.jwt.security.service.impl;

import com.jwt.security.model.User;
import com.jwt.security.repository.UserRepository;
import com.jwt.security.request.UserCreateRequest;
import com.jwt.security.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(final Long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public Optional<User> getUserByUsernameAndPassword(final String username, final String password) {
        return userRepository.getUserByUsernameAndPassword(username, password);
    }

    @Override
    public Optional<User> createUser(final UserCreateRequest userCreateRequest) {
        final User newUser = new User();

        newUser.setUsername(userCreateRequest.getUsername());
        newUser.setPassword(userCreateRequest.getPassword());

        userRepository.save(newUser);


        return Optional.empty();
    }
}
