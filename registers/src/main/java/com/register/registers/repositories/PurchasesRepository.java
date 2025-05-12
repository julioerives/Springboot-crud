package com.register.registers.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.register.registers.entities.Purchases;

public interface PurchasesRepository extends JpaRepository<Purchases,Long>  {
    @Query("""
        SELECT p 
        FROM Purchases p
        JOIN p.product pr
        JOIN pr.productType prt
        WHERE p.user.id = :userId AND (
            (:searchType = 'description' AND LOWER(p.description) LIKE LOWER(CONCAT('%', :name, '%')))
            OR (:searchType = 'product' AND LOWER(pr.name) LIKE LOWER(CONCAT('%', :name, '%')))
            OR (:searchType = 'productType' AND LOWER(prt.name) LIKE LOWER(CONCAT('%', :name, '%')))
        )
    """)
    public List<Purchases> findByFilters(@Param("name") String name, @Param("searchType") String searchType, Long userId, Pageable pageable );
    public Optional<List<Purchases>>  findByUserUserId(Long userId);
}
