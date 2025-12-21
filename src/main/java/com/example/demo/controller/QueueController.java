package com.example.demo.controller;

import com.example.demo.model.QueuePosition;
import com.example.demo.model.Token;
import com.example.demo.service.QueueService;
import com.example.demo.service.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;
    private final TokenService tokenService;

    public QueueController(QueueService queueService, TokenService tokenService) {
        this.queueService = queueService;
        this.tokenService = tokenService;
    }

    @PostMapping("/assign/{tokenId}")
    public QueuePosition assignQueue(@PathVariable Long tokenId){
        Token token = tokenService.getAllTokens()
                .stream()
                .filter(t -> t.getId().equals(tokenId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Token not found"));

        return queueService.assignPosition(token);
    }

    @GetMapping
    public List<QueuePosition> getQueue(){
        return queueService.getQueue();
    }
}
