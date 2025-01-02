package com.register.registers.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productTypeId;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private Users user;
    private String name;
    private String description;
    private String colorRgba;
    public Long getProductTypeId() {
        return productTypeId;
    }
    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }
    @OneToMany(mappedBy = "productType")
    private List<Product> products;
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
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
