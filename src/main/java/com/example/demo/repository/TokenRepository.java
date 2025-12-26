package com.example.demo.repository;

import com.example.demo.entity.Token;
import java.util.*;

public interface TokenRepository {
    Token save(Token token);
    Optional<Token> findById(Long id);
    Optional<Token> findByTokenNumber(String tokenNumber);
    List<Token> findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(Long id, String status);
}
