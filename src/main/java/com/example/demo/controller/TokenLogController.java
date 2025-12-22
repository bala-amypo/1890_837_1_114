package com.example.demo.controller;

import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class TokenLogController {

    private final TokenLogRepository logRepository;

    public TokenLogController(TokenLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping
    public List<TokenLog> getAllLogs(){
        return logRepository.findAll();
    }
}
