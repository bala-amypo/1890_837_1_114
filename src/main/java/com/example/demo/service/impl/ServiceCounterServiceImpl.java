package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository repo;

    public ServiceCounterServiceImpl(ServiceCounterRepository repo) {
        this.repo = repo;
    }

    @Override
    public ServiceCounter addCounter(ServiceCounter counter) {
        repo.save(counter);          // call repo
        return counter;              // return entity (IMPORTANT)
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return repo.findByIsActiveTrue();
    }
}
