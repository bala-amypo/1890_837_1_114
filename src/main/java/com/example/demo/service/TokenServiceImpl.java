package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String generateToken(Long userId) {
        return "TOKEN_" + userId;
    }
}
