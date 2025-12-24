package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Token;

public interface TokenService {

    Token generateToken(Long counterId);

    // STEP 5 — declaration
    List<Token> getAllTokens();
}
