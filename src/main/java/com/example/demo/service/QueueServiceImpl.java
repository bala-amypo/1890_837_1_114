package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.service.QueueService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service   // 🔥 THIS IS THE KEY
public class QueueServiceImpl implements QueueService {

    private final QueuePositionRepository queueRepo;

    public QueueServiceImpl(QueuePositionRepository queueRepo) {
        this.queueRepo = queueRepo;
    }

    @Override
    public List<QueuePosition> getQueue() {
        return queueRepo.findAll();
    }
}
