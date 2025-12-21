package com.example.demo.service;

import com.example.demo.model.Token;
import java.util.List;

public interface TokenService {
    Token createToken(Long counterId);
    Token updateStatus(Long tokenId, Token.Status status);
    List<Token> getAllTokens();
}
