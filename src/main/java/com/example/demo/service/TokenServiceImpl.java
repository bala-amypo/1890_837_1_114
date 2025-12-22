package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository logRepository;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository counterRepository,
                            TokenLogRepository logRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.logRepository = logRepository;
    }

    // 🔹 Generate Token
    @Override
    public Token generateToken(Long counterId) {

        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Service Counter not found"));

        if (!counter.getIsActive()) {
            throw new RuntimeException("Counter is not active");
        }

        Token token = new Token();
        token.setTokenNumber("T-" + System.currentTimeMillis());
        token.setServiceCounter(counter);
        token.setStatus("ISSUED");
        token.setIssuedAt(LocalDateTime.now());

        token = tokenRepository.save(token);

        TokenLog log = new TokenLog();
        log.setToken(token);
        log.setLogMessage("Token generated successfully");
        logRepository.save(log);

        return token;
    }

    // 🔥 STEP 5 — IMPLEMENTATION
    @Override
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}
