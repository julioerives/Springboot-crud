package com.register.registers.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType,Long> {
    List<ProductType> findByUserUserId(Long userId);
    Optional<ProductType> findByProductTypeIdAndUserUserId(Long productTypeId, Long userId);

}
