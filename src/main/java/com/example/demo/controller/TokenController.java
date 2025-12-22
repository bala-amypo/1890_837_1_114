package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService service;

    public TokenController(TokenService service) {
        this.service = service;
    }

    @PostMapping("/issue/{counterId}")
    public Token issueToken(@PathVariable Long counterId) {

        
        return service.generateToken(counterId);
    }
}
