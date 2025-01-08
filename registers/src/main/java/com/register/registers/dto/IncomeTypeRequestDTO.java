package com.register.registers.dto;

public class IncomeTypeRequestDTO {
    private Long userId;
    private String name;
    private String description;
    private String colorRgba;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
