package com.example.demo.serviceImpl;

import com.example.demo.model.Token;
import com.example.demo.model.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.service.TokenLogService;
import org.springframework.stereotype.Service;

@Service
public class TokenLogImpl implements TokenLogService {

    private final TokenLogRepository logRepository;

    public TokenLogImpl(TokenLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void log(Token token, String message) {
        TokenLog log = new TokenLog();
        log.setToken(token);
        log.setLogMessage(message);
        logRepository.save(log);
    }
}
