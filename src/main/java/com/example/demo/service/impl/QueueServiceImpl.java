package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.Token;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;

import java.util.Collections;
import java.util.List;

public class QueueServiceImpl implements QueueService {

    private final QueuePositionRepository queueRepo;
    private final TokenRepository tokenRepo;

    public QueueServiceImpl(QueuePositionRepository queueRepo,
                            TokenRepository tokenRepo) {
        this.queueRepo = queueRepo;
        this.tokenRepo = tokenRepo;
    }

    @Override
    public QueuePosition updateQueuePosition(Long tokenId, int position) {
        if (position < 1) {
            throw new IllegalArgumentException(">= 1");
        }

        Token token = tokenRepo.findById(tokenId).orElseThrow();

        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(position);

        return queueRepo.save(qp);
    }

    @Override
    public QueuePosition getPosition(Long tokenId) {
        return queueRepo.findByToken_Id(tokenId).orElseThrow();
    }

    @Override
    public List<QueuePosition> getQueue() {
        return Collections.emptyList();
    }
}
