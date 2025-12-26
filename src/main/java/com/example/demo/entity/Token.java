package com.example.demo.entity;

import java.time.LocalDateTime;

public class Token {
    private Long id;
    private String tokenNumber;
    private String status;
    private LocalDateTime completedAt;
    private ServiceCounter serviceCounter;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTokenNumber() { return tokenNumber; }
    public void setTokenNumber(String tokenNumber) { this.tokenNumber = tokenNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public ServiceCounter getServiceCounter() { return serviceCounter; }
    public void setServiceCounter(ServiceCounter serviceCounter) { this.serviceCounter = serviceCounter; }
}
