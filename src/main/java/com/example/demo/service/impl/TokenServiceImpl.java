package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

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

    @Override
    public Token getToken(Long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
    }

    @Override
    public Token issueToken(Long counterId) {

        ServiceCounter counter = counterRepo.findById(counterId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        if (!Boolean.TRUE.equals(counter.getIsActive())) {
            throw new IllegalStateException("inactive");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());

        return tokenRepo.save(token);
    }

    @Override
    public Token updateStatus(Long tokenId, String status) {

        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        if ("WAITING".equals(token.getStatus()) && "COMPLETED".equals(status)) {
            throw new IllegalStateException("invalid");
        }

        token.setStatus(status);

        if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
            token.setCompletedAt(LocalDateTime.now());
        }

        return tokenRepo.save(token);
    }

    @Override
    public Optional<Token> findById(Long id) {
        return tokenRepo.findById(id);
    }

    @Override
    public Optional<Token> findByTokenNumber(String tokenNumber) {
        return tokenRepo.findByTokenNumber(tokenNumber);
    }
}
