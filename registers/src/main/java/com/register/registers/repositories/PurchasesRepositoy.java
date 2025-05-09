package com.register.registers.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.register.registers.entities.Purchases;

public interface PurchasesRepositoy extends JpaRepository<Purchases,Long>  {
    @Query("""
        SELECT 
            P.*,
            PR.name,
            PR.price,
            PRT.name

        FROM purchase P
        JOIN products PR ON PR.user_id = :userId
        JOIN product_type PRT ON PRT.user_id = :userId AND PRT.product_type_id = PR.product_type_id
        WHERE (
        (:searchType = 'description' AND P.description LIKE '%:name%')
        OR
        (:searchType = 'product' AND PR.name LIKE '%:name%')
        OR
        (:searchType = 'productType' AND PRT.name LIKE '%:name%')
        )
    """)
    public List<Purchases> findByFilters(@Param("name") String name, @Param("searchType") String searchType, Long userId, Pageable pageable );
    public Optional<List<Purchases>>  findByUserUserId(Long userId);
}
