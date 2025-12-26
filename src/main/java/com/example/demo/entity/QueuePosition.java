package com.example.demo.entity;

public class QueuePosition {
    private Long id;
    private Integer position;
    private Token token;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }
}
