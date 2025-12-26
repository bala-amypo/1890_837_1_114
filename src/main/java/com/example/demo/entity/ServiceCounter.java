package com.example.demo.entity;

public class ServiceCounter {
    private Long id;
    private String counterName;
    private String department;
    private Boolean isActive = true; // MUST be true by default


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCounterName() { return counterName; }
    public void setCounterName(String counterName) { this.counterName = counterName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Boolean getIsActive() {
    return isActive;
}

}
