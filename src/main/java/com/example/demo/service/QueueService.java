package com.example.demo.service;

import com.example.demo.model.QueuePosition;
import com.example.demo.model.Token;

import java.util.List;

public interface QueueService {
    QueuePosition assignPosition(Token token);
    List<QueuePosition> getQueue();
}
