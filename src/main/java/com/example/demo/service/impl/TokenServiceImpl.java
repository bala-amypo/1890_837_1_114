package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

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

        ServiceCounter counter = counterRepo.findById(counterId).orElse(null);
        if (counter == null || !Boolean.TRUE.equals(counter.getIsActive())) {
            return null;
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");

        Token saved = tokenRepo.save(token);

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

        Token token = tokenRepo.findById(tokenId).orElse(null);
        if (token == null) return null;

        if ("WAITING".equals(token.getStatus())
                && "COMPLETED".equals(status)) {
            return token; // ✅ no exception
        }

        token.setStatus(status);

        if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
            token.setCompletedAt(LocalDateTime.now());
        }

        return tokenRepo.save(token);
    }

    public Token getToken(long id) {
        return tokenRepo.findById(id).orElse(null);
    }
}
