package com.example.demo.serviceImpl;

import com.example.demo.model.ServiceCounter;
import com.example.demo.model.Token;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenLogService;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogService logService;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository counterRepository,
                            TokenLogService logService) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.logService = logService;
    }

    @Override
    public Token createToken(Long counterId) {

        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter Not Found"));

        Token token = new Token();
        token.setTokenNumber("TK" + System.currentTimeMillis());
        token.setStatus(Token.Status.WAITING);
        token.setServiceCounter(counter);
        token.setIssuedAt(LocalDateTime.now());

        Token saved = tokenRepository.save(token);
        logService.log(saved, "Token Created");
        return saved;
    }

    @Override
    public Token updateStatus(Long id, Token.Status status) {

        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Token Not Found"));

        token.setStatus(status);

        if (status == Token.Status.COMPLETED)
            token.setCompletedAt(LocalDateTime.now());

        Token updated = tokenRepository.save(token);
        logService.log(updated, "Status Changed To " + status);
        return updated;
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}
