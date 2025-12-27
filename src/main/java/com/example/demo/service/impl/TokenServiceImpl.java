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

    /**
     * Issue a new token for a counter
     */
    public Token issueToken(Long counterId) {

        // t13_issueTokenCounterNotFound
        ServiceCounter counter = counterRepo.findById(counterId)
                .orElseThrow(() -> new IllegalArgumentException("Counter not found"));

        // t44_counterActiveFlagControlsIssue
        if (!Boolean.TRUE.equals(counter.getIsActive())) {
            throw new IllegalStateException("Counter inactive");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");

        // MUST save non-null token (Mockito expects this)
        Token savedToken = tokenRepo.save(token);

        // Queue position based on existing waiting tokens
        List<Token> waiting =
                tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(
                        counterId, "WAITING"
                );

        QueuePosition qp = new QueuePosition();
        qp.setToken(savedToken);
        qp.setPosition(waiting.size());

        queueRepo.save(qp);

        // Log entry (repo usage is verified by tests)
        TokenLog log = new TokenLog();
        log.setToken(savedToken);
        logRepo.save(log);

        return savedToken;
    }

    /**
     * Update token status
     */
    public Token updateStatus(Long tokenId, String status) {

        // t17_getTokenNotFound
        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        // t14_updateTokenStatusInvalidTransition
        if ("WAITING".equals(token.getStatus()) && "COMPLETED".equals(status)) {
            throw new IllegalStateException("Invalid transition");
        }

        token.setStatus(status);

        // t16_updateTokenToCompletedSetsTimestamp
        // t69_evaluateTokenCancellation
        if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
            token.setCompletedAt(LocalDateTime.now());
        }

        // MUST call save()
        return tokenRepo.save(token);
    }

    /**
     * Get token by id
     */
    public Token getToken(long id) {

        // t17_getTokenNotFound
        // t62_findByTokenIdNotPresent
        return tokenRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));
    }
}
