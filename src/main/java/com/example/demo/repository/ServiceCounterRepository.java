package com.example.demo.repository;

import com.example.demo.entity.ServiceCounter;
import java.util.*;

public interface ServiceCounterRepository {
    ServiceCounter save(ServiceCounter sc);
    Optional<ServiceCounter> findById(Long id);
    List<ServiceCounter> findByIsActiveTrue();
}
