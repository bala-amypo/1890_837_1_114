package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
    public BreachAlert issueToken(Long counterId) {

        ServiceCounter counter = counterRepo.findById(counterId)
                .orElseThrow(() -> new ResourceNotFoundException("Counter not found"));

        if(Boolean.FALSE.equals(counter.getIsActive())){
            throw new IllegalArgumentException("Counter not active");
        }

        BreachAlert alert = new BreachAlert();
        alert.setTokenNumber("ALERT-" + UUID.randomUUID());
        alert.setStatus("OPEN");
        alert.setIssuedAt(LocalDateTime.now());

        BreachAlert saved = tokenRepo.save(alert);

        // Queue
        QueuePosition qp = new QueuePosition(saved, 1, LocalDateTime.now());
        queueRepo.save(qp);

        // Log
        logRepo.save(new TokenLog(saved, "Token Issued", LocalDateTime.now()));

        return saved;
    }

    @Override
    public BreachAlert updateStatus(Long tokenId, String status) {

        BreachAlert token = tokenRepo.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        if(!isValidTransition(token.getStatus(), status)){
            throw new IllegalArgumentException("Invalid status");
        }

        token.setStatus(status);

        if(status.equals("RESOLVED") || status.equals("CANCELLED")){
            token.setResolvedAt(LocalDateTime.now());
        }

        BreachAlert updated = tokenRepo.save(token);

        logRepo.save(new TokenLog(updated,"Status changed to " + status, LocalDateTime.now()));

        return updated;
    }

    private boolean isValidTransition(String current, String next){
        if(current.equals("OPEN") && (next.equals("ACKNOWLEDGED") || next.equals("CANCELLED"))) return true;
        if(current.equals("ACKNOWLEDGED") && (next.equals("RESOLVED") || next.equals("CANCELLED"))) return true;
        return false;
    }

    @Override
    public BreachAlert getToken(Long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }
}
