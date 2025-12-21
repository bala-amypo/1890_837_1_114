package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_log")
public class TokenLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Token token;

    private String logMessage;

    private LocalDateTime loggedAt;

    @PrePersist
    public void setTime() {
        this.loggedAt = LocalDateTime.now();
    }

    // getters & setters
}
