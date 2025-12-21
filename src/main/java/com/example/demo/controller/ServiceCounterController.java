package com.example.demo.controller;

import com.example.demo.model.ServiceCounter;
import com.example.demo.service.ServiceCounterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counter")
public class ServiceCounterController {

    private final ServiceCounterService counterService;

    public ServiceCounterController(ServiceCounterService counterService) {
        this.counterService = counterService;
    }

    @PostMapping
    public ServiceCounter createCounter(@RequestBody ServiceCounter counter){
        return counterService.createCounter(counter);
    }

    @GetMapping
    public List<ServiceCounter> getAllCounters(){
        return counterService.getAllCounters();
    }
}
