package com.example.demo.service;

import com.example.demo.entity.Token;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public Token generateToken(Long userId) {
        Token token = new Token();
        token.setTokenValue("TOKEN_" + userId);
        return token;
    }
}
