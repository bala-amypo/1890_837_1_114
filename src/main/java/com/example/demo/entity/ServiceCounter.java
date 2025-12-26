package com.example.demo.entity;

public class ServiceCounter {

    private Long id;
    private String counterName;
    private String department;

    // IMPORTANT: default must be true (test t33)
    private Boolean isActive = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // ===== THIS GETTER + SETTER ARE REQUIRED BY TESTS =====

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
