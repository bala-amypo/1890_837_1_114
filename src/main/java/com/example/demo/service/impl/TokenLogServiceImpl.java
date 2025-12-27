package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import java.util.List;

public class TokenLogServiceImpl {

    private final TokenLogRepository logRepo;
    private final TokenRepository tokenRepo;

    public TokenLogServiceImpl(TokenLogRepository logRepo,
                               TokenRepository tokenRepo) {
        this.logRepo = logRepo;
        this.tokenRepo = tokenRepo;
    }

    public TokenLog addLog(Long tokenId, String message) {

        Token token = tokenRepo.findById(tokenId).orElse(null);
        if (token == null) return null;

        TokenLog log = new TokenLog();
        log.setToken(token);

        return logRepo.save(log);
    }

    public List<TokenLog> getLogs(Long tokenId) {
        return logRepo.findByToken_IdOrderByLoggedAtAsc(tokenId);
    }
}
