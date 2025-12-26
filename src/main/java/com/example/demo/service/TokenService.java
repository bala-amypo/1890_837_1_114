package com.example.demo.service;

import com.example.demo.entity.Token;

public interface TokenService {
    Token generateToken(Long userId);
}
