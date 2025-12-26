package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;

import java.util.List;

public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository repo;

    public ServiceCounterServiceImpl(ServiceCounterRepository repo) {
        this.repo = repo;
    }

    @Override
    public ServiceCounter addCounter(ServiceCounter input) {

        ServiceCounter sc = new ServiceCounter();   // 🔑 NEW OBJECT
        sc.setCounterName(input.getCounterName());
        sc.setDepartment(input.getDepartment());
        sc.setIsActive(input.getIsActive());

        return repo.save(sc);                       // NEVER null
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return repo.findByIsActiveTrue();
    }
}
