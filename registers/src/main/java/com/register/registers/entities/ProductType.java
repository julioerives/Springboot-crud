package com.register.registers.entities;

import java.util.List;

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
    private Long product_type_id;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;
    private String name;
    private String description;
    private String colorRgba;
    @OneToMany(mappedBy = "productType")
    private List<Product> products;
    public Long getProduct_type_id() {
        return product_type_id;
    }
    public void setProduct_type_id(Long product_type_id) {
        this.product_type_id = product_type_id;
    }
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
