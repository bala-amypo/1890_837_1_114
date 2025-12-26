package com.example.demo.service;

import com.example.demo.entity.ServiceCounter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final List<ServiceCounter> counters = new ArrayList<>();

    @Override
    public ServiceCounter addCounter(ServiceCounter counter) {
        counters.add(counter);
        return counter;
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return counters;
    }
}
