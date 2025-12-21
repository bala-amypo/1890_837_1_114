package com.example.demo.serviceImpl;

import com.example.demo.model.QueuePosition;
import com.example.demo.model.Token;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.service.QueueService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueuePositionRepository queueRepo;

    public QueueServiceImpl(QueuePositionRepository queueRepo) {
        this.queueRepo = queueRepo;
    }

    @Override
    public QueuePosition assignPosition(Token token) {

        QueuePosition queue = new QueuePosition();
        queue.setToken(token);
        queue.setPosition(1);
        queue.setUpdatedAt(LocalDateTime.now());

        return queueRepo.save(queue);
    }

    @Override
    public List<QueuePosition> getQueue() {
        return queueRepo.findAll();
    }
}
