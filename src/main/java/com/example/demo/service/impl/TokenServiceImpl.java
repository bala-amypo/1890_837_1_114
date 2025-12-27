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

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");

        Token saved = tokenRepo.save(token);

        QueuePosition qp = new QueuePosition();
        qp.setToken(saved);
        qp.setPosition(
                tokenRepo
                        .findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(
                                counterId, "WAITING"
                        ).size()
        );

        queueRepo.save(qp);
        logRepo.save(new TokenLog());

        return saved;
    }

    public Token updateStatus(Long tokenId, String status) {

        Token token = tokenRepo.findById(tokenId).orElse(null);

        if (token == null) {
            return null;
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
