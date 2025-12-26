package com.example.demo.service;

public class ServiceCounterServiceImpl implements ServiceCounterService {
    private int count = 0;

    @Override
    public int increment() {
        return ++count;
    }
}
