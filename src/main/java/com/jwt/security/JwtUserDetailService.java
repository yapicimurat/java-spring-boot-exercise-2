package com.jwt.security;

import com.jwt.security.model.User;
import com.jwt.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class JwtUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User foundedUser = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found..."));

        System.out.println(foundedUser.getUsername() + " " + foundedUser.getPassword() + " " + foundedUser.getId());

        return new org.springframework.security.core.userdetails.User(foundedUser.getUsername(), foundedUser.getPassword(),new ArrayList<>());
    }
}
