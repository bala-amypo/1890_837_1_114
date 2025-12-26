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

        // 🔴 NEVER null
        Token token = new Token();
        token.setServiceCounter(sc);
        token.setStatus("WAITING");
        token.setTokenNumber(sc.getCounterName() + "-" + System.currentTimeMillis());

        Token saved = tokenRepo.save(token); // must receive non-null

        List<Token> waiting =
                tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING");

        QueuePosition qp = new QueuePosition();
        qp.setToken(saved);
        qp.setPosition(waiting.size());

        queueRepo.save(qp);
        logRepo.save(new TokenLog());

        return saved;
    }

    public Token updateStatus(Long tokenId, String status) {
        Token t = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("not found"));

        if (t.getStatus().equals("WAITING") && status.equals("COMPLETED")) {
            throw new IllegalArgumentException("Invalid status");
        }

        t.setStatus(status);

        if (status.equals("COMPLETED") || status.equals("CANCELLED")) {
            t.setCompletedAt(LocalDateTime.now());
        }

        tokenRepo.save(t);        // t is never null
        logRepo.save(new TokenLog());

        return t;
    }

    public Token getToken(Long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
}
