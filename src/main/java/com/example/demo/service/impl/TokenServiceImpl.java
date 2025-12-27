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

        ServiceCounter counter = counterRepo.findById(counterId)
                .orElseThrow(IllegalArgumentException::new);

        if (!Boolean.TRUE.equals(counter.getIsActive())) {
            throw new IllegalStateException();
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");

        Token saved = tokenRepo.save(token);   // MUST happen

        List<Token> waiting =
                tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(
                        counterId, "WAITING"
                );

        QueuePosition qp = new QueuePosition();
        qp.setToken(saved);
        qp.setPosition(waiting.size());

        queueRepo.save(qp);                    // MUST happen

        TokenLog log = new TokenLog();
        log.setToken(saved);
        logRepo.save(log);                     // MUST happen

        return saved;
    }

    public Token updateStatus(Long tokenId, String status) {

        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(IllegalArgumentException::new);

        if ("WAITING".equals(token.getStatus()) &&
            "COMPLETED".equals(status)) {
            throw new IllegalStateException();
        }

        token.setStatus(status);

        if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
            token.setCompletedAt(LocalDateTime.now());
        }

        return tokenRepo.save(token);           // MUST happen
    }

    public Token getToken(long id) {
        return tokenRepo.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
