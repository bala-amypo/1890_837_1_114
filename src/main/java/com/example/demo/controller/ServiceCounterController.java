package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.ServiceCounterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counters")
@Tag(name = "Service Counters")
public class ServiceCounterController {

    private final ServiceCounterService service;

    public ServiceCounterController(ServiceCounterService service) {
        this.service = service;
    }

    @PostMapping
    public ServiceCounter add(@RequestBody ServiceCounter c) {
        return service.addCounter(c);
    }

    @GetMapping("/active")
    public List<ServiceCounter> active() {
        return service.getActiveCounters();
    }
}
