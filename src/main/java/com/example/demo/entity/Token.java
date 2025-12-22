package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "tokenNumber"))
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenNumber;

    @ManyToOne
    private ServiceCounter serviceCounter;

    private String status;

    private LocalDateTime issuedAt;
    private LocalDateTime completedAt;

    public void setTokenNumber(String tokenNumber) {
    this.tokenNumber = tokenNumber;
}

public void setServiceCounter(ServiceCounter serviceCounter) {
    this.serviceCounter = serviceCounter;
}

public void setStatus(String status) {
    this.status = status;
}

public void setIssuedAt(LocalDateTime issuedAt) {
    this.issuedAt = issuedAt;
}

public Long getId() {
    return id;
}



    // getters & setters
}
