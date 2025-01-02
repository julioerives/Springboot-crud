package com.register.registers.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
        List<Product> findByUserUserId(Long userId);

}
