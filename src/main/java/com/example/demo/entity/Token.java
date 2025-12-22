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

    // getters & setters
}
