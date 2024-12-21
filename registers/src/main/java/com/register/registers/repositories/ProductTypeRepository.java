package com.register.registers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType,Long> {
    
}
