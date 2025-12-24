package com.example.demo.service;

import com.example.demo.entity.Token;

public interface TokenLogService {
    void log(Token token, String message);
}
