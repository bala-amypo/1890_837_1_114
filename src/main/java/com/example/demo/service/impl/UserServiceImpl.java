package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.Base64;

public class UserServiceImpl {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    public User register(User input) {
        if (repo.findByEmail(input.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();          // 🔑 NEW OBJECT
        user.setEmail(input.getEmail());
        user.setPassword(
                Base64.getEncoder().encodeToString(input.getPassword().getBytes())
        );

        return repo.save(user);          // NEVER null
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow();
    }
}
