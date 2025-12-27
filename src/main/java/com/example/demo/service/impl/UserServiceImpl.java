package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import java.util.Base64;

public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
public User register(User user) {

    if (repo.findByEmail(user.getEmail()).isPresent()) {
        throw new IllegalArgumentException();   // REQUIRED by t9
    }

    user.setPassword(
            java.util.Base64.getEncoder()
                    .encodeToString(user.getPassword().getBytes())
    );

    return repo.save(user);  // MUST be called
}


    @Override
    public User findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
