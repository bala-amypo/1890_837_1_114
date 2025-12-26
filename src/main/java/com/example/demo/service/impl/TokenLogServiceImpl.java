

package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;

import java.util.List;

public class TokenLogServiceImpl {

    private final TokenLogRepository logRepo;
    private final TokenRepository tokenRepo;

    public TokenLogServiceImpl(TokenLogRepository logRepo,
                               TokenRepository tokenRepo) {
        this.logRepo = logRepo;
        this.tokenRepo = tokenRepo;
    }

    public TokenLog addLog(Long tokenId, String msg) {
        Token token = tokenRepo.findById(tokenId).orElseThrow();

        TokenLog log = new TokenLog();
        log.setToken(token);

        return logRepo.save(log);   // ✅ non-null
    }

    public List<TokenLog> getLogs(Long tokenId) {
        return logRepo.findByToken_IdOrderByLoggedAtAsc(tokenId);
    }
}
