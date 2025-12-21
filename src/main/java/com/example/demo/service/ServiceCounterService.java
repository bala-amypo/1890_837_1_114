package com.example.demo.service;

import com.example.demo.model.ServiceCounter;
import java.util.List;

public interface ServiceCounterService {
    ServiceCounter createCounter(ServiceCounter counter);
    List<ServiceCounter> getAllCounters();
}
