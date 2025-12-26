package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.Base64;

public class UserServiceImpl {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

   public User register(User user) {

    if (repo.findByEmail(user.getEmail()).isPresent()) {
        throw new IllegalArgumentException("Email already exists");
    }

    user.setPassword(
            java.util.Base64.getEncoder()
                    .encodeToString(user.getPassword().getBytes())
    );

    return repo.save(user);   // single save
}

    public User findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow();
    }
}
