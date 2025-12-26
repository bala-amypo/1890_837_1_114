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

    Token token = new Token();        // ✅ non-null
    token.setServiceCounter(sc);
    token.setStatus("WAITING");
    token.setTokenNumber(
        sc.getCounterName() + "-" + System.currentTimeMillis()
    );

    Token saved = tokenRepo.save(token);  // ✅ ALWAYS called with token

    QueuePosition qp = new QueuePosition();
    qp.setToken(saved);
    qp.setPosition(
        tokenRepo.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(
            counterId, "WAITING"
        ).size()
    );

    queueRepo.save(qp);               // ✅ non-null
    logRepo.save(new TokenLog());     // ✅ non-null

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
        t.setCompletedAt(java.time.LocalDateTime.now());
    }

    // ❌ NO save() here
    return t;
}


    public Token getToken(Long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
}
