package com.example.demo.service;

import com.example.demo.entity.Token;

import java.util.Optional;

public interface TokenService {

    Token issueToken(Long counterId);

    Token updateStatus(Long tokenId, String status);

    Optional<Token> findById(Long id);

    Optional<Token> findByTokenNumber(String tokenNumber);
}
