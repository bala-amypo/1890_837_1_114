package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthCounterController {

    private final UserService userService;

    public AuthCounterController(UserService userService) {
        this.userService = userService;
    }
}
