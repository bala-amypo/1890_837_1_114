package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User register(User user) {

        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("duplicate");
        }

        user.setPassword(Integer.toHexString(user.getPassword().hashCode()));

        repo.save(user);
        return user;   // IMPORTANT
    }

    @Override
    public User findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
    }
}
