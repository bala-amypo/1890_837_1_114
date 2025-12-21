package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token", uniqueConstraints = @UniqueConstraint(columnNames = "tokenNumber"))
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tokenNumber;

    @ManyToOne
    @JoinColumn(name = "service_counter_id")
    private ServiceCounter serviceCounter;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime issuedAt;
    private LocalDateTime completedAt;

    public enum Status {
        WAITING, SERVING, COMPLETED, CANCELLED
    }

    // getters & setters
}
