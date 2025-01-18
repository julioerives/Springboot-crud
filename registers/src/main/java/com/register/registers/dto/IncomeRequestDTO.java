package com.register.registers.dto;

import java.time.LocalDate;

public class IncomeRequestDTO {
    private Long userId;
    private Long incomeTypeId;
    private Float quantity;
    private String description;
    private LocalDate date;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getIncomeTypeId() {
        return incomeTypeId;
    }
    public void setIncomeTypeId(Long incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
    }
    public Float getQuantity() {
        return quantity;
    }
    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    
}
