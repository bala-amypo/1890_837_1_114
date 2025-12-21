package com.example.demo.controller;

import com.example.demo.model.Token;
import com.example.demo.service.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/create/{counterId}")
    public Token createToken(@PathVariable Long counterId){
        return tokenService.createToken(counterId);
    }

    @PutMapping("/{tokenId}/status/{status}")
    public Token updateStatus(@PathVariable Long tokenId, @PathVariable Token.Status status){
        return tokenService.updateStatus(tokenId, status);
    }

    @GetMapping
    public List<Token> getAllTokens(){
        return tokenService.getAllTokens();
    }
}
