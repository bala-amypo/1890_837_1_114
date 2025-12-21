package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "service_counter")
public class ServiceCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String counterName;
    private String department;
    private Boolean isActive = true;

    // getters & setters
}
