package com.register.registers.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class IncomeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeTypeId;
    private String name;
    private String description;
    private String colorRgba;
    
    public Long getIncomeTypeId() {
        return incomeTypeId;
    }
    public void setIncomeTypeId(Long incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getColorRgba() {
        return colorRgba;
    }
    public void setColorRgba(String colorRgba) {
        this.colorRgba = colorRgba;
    }
    
}
