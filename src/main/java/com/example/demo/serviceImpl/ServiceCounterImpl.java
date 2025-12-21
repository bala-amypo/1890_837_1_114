package com.example.demo.serviceImpl;

import com.example.demo.model.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCounterImpl implements ServiceCounterService {

    private final ServiceCounterRepository repository;

    public ServiceCounterImpl(ServiceCounterRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceCounter createCounter(ServiceCounter counter) {
        return repository.save(counter);
    }

    @Override
    public List<ServiceCounter> getAllCounters() {
        return repository.findAll();
    }
}
