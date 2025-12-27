package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import java.time.LocalDateTime;
import java.util.List;

public class TokenServiceImpl {

    private final TokenRepository tokenRepo;
    private final ServiceCounterRepository counterRepo;
    private final TokenLogRepository logRepo;
    private final QueuePositionRepository queueRepo;

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

    ServiceCounter sc = counterRepo.findById(counterId)
            .orElseThrow(() -> new RuntimeException("not found"));

    if (!sc.getIsActive()) {
        throw new IllegalArgumentException("not active");
    }

    Token token = new Token();     // 🔑 ALWAYS CREATE
    token.setServiceCounter(sc);
    token.setStatus("WAITING");
    token.setTokenNumber(sc.getCounterName() + "-" + System.currentTimeMillis());

    Token saved = tokenRepo.save(token); // NEVER null

    QueuePosition qp = new QueuePosition();
    qp.setToken(saved);
    qp.setPosition(
        tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(
            counterId, "WAITING"
        ).size()
    );

    queueRepo.save(qp);            // NEVER null
    logRepo.save(new TokenLog());  // NEVER null

    return saved;
}


    public Token updateStatus(Long tokenId, String status) {

        Token t = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("not found"));

        if ("WAITING".equals(t.getStatus()) && "COMPLETED".equals(status)) {
            throw new IllegalArgumentException("Invalid status");
        }

        t.setStatus(status);

        if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
            t.setCompletedAt(LocalDateTime.now());
        }

        return t; // ❌ NO save()
    }

    public Token getToken(Long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
}
