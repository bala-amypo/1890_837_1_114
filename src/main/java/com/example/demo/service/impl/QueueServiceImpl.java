package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
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

        if (position <= 0) {
            throw new IllegalArgumentException();
        }

        Token token = tokenRepo.findById(tokenId)
                .orElseThrow(IllegalArgumentException::new);

        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(position);

        return queueRepo.save(qp);               // MUST happen
    }

    @Override
    public QueuePosition getPosition(Long tokenId) {
        return queueRepo.findByToken_Id(tokenId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<QueuePosition> getQueue() {
        return Collections.emptyList();
    }
}
