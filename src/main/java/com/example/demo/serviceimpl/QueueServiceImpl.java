package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.QueueService;

import java.util.*;

public class QueueServiceImpl implements QueueService {

    private final QueuePositionRepository queueRepo;
    private final TokenRepository tokenRepo;

    public QueueServiceImpl(QueuePositionRepository q, TokenRepository t) {
        this.queueRepo = q;
        this.tokenRepo = t;
    }

    public QueuePosition updateQueuePosition(Long tokenId, int position) {
        Token token = tokenRepo.findById(tokenId).orElseThrow();
        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(position);
        return queueRepo.save(qp);
    }

    public QueuePosition getPosition(Long tokenId) {
        return queueRepo.findByToken_Id(tokenId).orElseThrow();
    }

    public List<QueuePosition> getQueue() {
        return Collections.emptyList();
    }
}
