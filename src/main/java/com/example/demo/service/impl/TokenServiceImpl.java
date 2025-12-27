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

    public TokenServiceImpl(TokenRepository t, ServiceCounterRepository c,
                            TokenLogRepository l, QueuePositionRepository q) {
        this.tokenRepo = t;
        this.counterRepo = c;
        this.logRepo = l;
        this.queueRepo = q;
    }

    public Token issueToken(Long counterId) {
        ServiceCounter sc = counterRepo.findById(counterId).orElseThrow();
        Token token = new Token();
        token.setServiceCounter(sc);
        token.setStatus("WAITING");

        Token saved = tokenRepo.save(token);

        QueuePosition qp = new QueuePosition();
        qp.setToken(saved);
        qp.setPosition(
                tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING").size()
        );

        queueRepo.save(qp);
        logRepo.save(new TokenLog());
        return saved;
    }

    public Token updateStatus(Long tokenId, String status) {
        Token t = tokenRepo.findById(tokenId).orElseThrow();
        t.setStatus(status);
        if ("COMPLETED".equals(status)) {
            t.setCompletedAt(LocalDateTime.now());
        }
        return tokenRepo.save(t);
    }
}
