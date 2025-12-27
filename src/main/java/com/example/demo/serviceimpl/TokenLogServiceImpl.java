package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import java.util.List;

public class TokenLogServiceImpl {

    private final TokenLogRepository logRepo;
    private final TokenRepository tokenRepo;

    public TokenLogServiceImpl(TokenLogRepository l, TokenRepository t) {
        this.logRepo = l;
        this.tokenRepo = t;
    }

    public TokenLog addLog(Long tokenId, String msg) {
        TokenLog log = new TokenLog();
        log.setToken(tokenRepo.findById(tokenId).orElseThrow());
        return logRepo.save(log);
    }

    public List<TokenLog> getLogs(Long tokenId) {
        return logRepo.findByToken_IdOrderByLoggedAtAsc(tokenId);
    }
}
