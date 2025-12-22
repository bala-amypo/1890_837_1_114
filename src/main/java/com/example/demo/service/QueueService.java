package com.example.demo.service;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.Token;

import java.util.List;

public interface QueueService {
    QueuePosition assignPosition(Token token);
    List<QueuePosition> getQueue();
}
