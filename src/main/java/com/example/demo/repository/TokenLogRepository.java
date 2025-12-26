package com.example.demo.repository;

import com.example.demo.entity.TokenLog;
import java.util.List;

public interface TokenLogRepository {
    TokenLog save(TokenLog log);
    List<TokenLog> findByToken_IdOrderByLoggedAtAsc(Long tokenId);
}
