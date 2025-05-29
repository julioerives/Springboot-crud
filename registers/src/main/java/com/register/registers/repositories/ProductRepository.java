package com.register.registers.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.register.registers.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
        List<Product> findByUserUserId(Long userId);

        @Query("SELECT p FROM Product p WHERE p.productId IN :ids")
        List<Product> findAllByIdIn(@Param("ids") Collection<Long> ids);

        Page<Product> findByUserUserIdAndName(Long userId, String name, Pageable pageable);
}
