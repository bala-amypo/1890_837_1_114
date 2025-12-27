package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TokenServiceImpl {

    private final TokenRepository tokenRepo;
    private final ServiceCounterRepository counterRepo;
    private final QueuePositionRepository queueRepo;
    private final TokenLogRepository logRepo;

    public TokenServiceImpl(TokenRepository tokenRepo,
                            ServiceCounterRepository counterRepo,
                            TokenLogRepository logRepo,
                            QueuePositionRepository queueRepo) {
        this.tokenRepo = tokenRepo;
        this.counterRepo = counterRepo;
        this.logRepo = logRepo;
        this.queueRepo = queueRepo;
    }

    public Token issueToken(Long counterId) {

        ServiceCounter counter = counterRepo.findById(counterId)
                .orElseThrow(() -> new IllegalArgumentException("Counter not found"));

        if (!Boolean.TRUE.equals(counter.getIsActive())) {
            throw new IllegalStateException("Counter inactive");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");

        Token saved = tokenRepo.save(token);   // MUST be non-null

        List<Token> waiting =
                tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(
                        counterId, "WAITING"
                );

        QueuePosition qp = new QueuePosition();
        qp.setToken(saved);
        qp.setPosition(waiting.size());

        queueRepo.save(qp);

        TokenLog log = new TokenLog();
        log.setToken(saved);
        logRepo.save(log);

        return saved;
    }

    public Token updateStatus(Long tokenId, String status) {

        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        if ("WAITING".equals(token.getStatus()) && "COMPLETED".equals(status)) {
            throw new IllegalStateException("Invalid transition");
        }

        token.setStatus(status);

        if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
            token.setCompletedAt(LocalDateTime.now());
        }

        return tokenRepo.save(token);   // MUST be called
    }

    public Token getToken(long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));
    }
}
