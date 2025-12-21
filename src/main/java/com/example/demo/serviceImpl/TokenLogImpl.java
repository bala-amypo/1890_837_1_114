package com.example.demo.service.impl;

import com.example.demo.entity.BreachAlert;
import com.example.demo.entity.TokenLog;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenLogServiceImpl implements TokenLogService {

    private final TokenLogRepository logRepo;
    private final TokenRepository tokenRepo;

    public TokenLogServiceImpl(TokenLogRepository logRepo, TokenRepository tokenRepo) {
        this.logRepo = logRepo;
        this.tokenRepo = tokenRepo;
    }

    @Override
    public TokenLog addLog(Long tokenId, String message) {

        BreachAlert token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        TokenLog log = new TokenLog(token, message, LocalDateTime.now());
        return logRepo.save(log);
    }

    @Override
    public List<TokenLog> getLogs(Long tokenId) {
        return logRepo.findByTokenIdOrderByLoggedAtAsc(tokenId);
    }
}
