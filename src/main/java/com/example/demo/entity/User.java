package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role = "STAFF";

    public String getEmail() {
    return email;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}



    // getters & setters
}
