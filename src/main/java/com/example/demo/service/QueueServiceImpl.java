package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

public class QueueServiceImpl {

    private final QueuePositionRepository queueRepo;
    private final TokenRepository tokenRepo;

    public QueueServiceImpl(QueuePositionRepository q, TokenRepository t) {
        this.queueRepo = q;
        this.tokenRepo = t;
    }

    public QueuePosition updateQueuePosition(Long tokenId, int pos) {
        if (pos < 1) throw new IllegalArgumentException(">= 1");
        Token t = tokenRepo.findById(tokenId).orElseThrow();
        QueuePosition qp = new QueuePosition();
        qp.setToken(t);
        qp.setPosition(pos);
        return queueRepo.save(qp);
    }

    public QueuePosition getPosition(Long tokenId) {
        return queueRepo.findByToken_Id(tokenId).orElseThrow();
    }
}
