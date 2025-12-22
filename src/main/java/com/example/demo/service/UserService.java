package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(User user) {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }
}
