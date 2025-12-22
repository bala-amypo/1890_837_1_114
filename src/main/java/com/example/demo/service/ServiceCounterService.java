package com.example.demo.service;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCounterService {

    private final ServiceCounterRepository repo;

    public ServiceCounterService(ServiceCounterRepository repo) {
        this.repo = repo;
    }

    public ServiceCounter addCounter(ServiceCounter c) {
        return repo.save(c);
    }

    public List<ServiceCounter> getActiveCounters() {
        return repo.findByIsActiveTrue();
    }
}
