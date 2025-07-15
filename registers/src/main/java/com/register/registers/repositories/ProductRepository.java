package com.register.registers.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.register.model.projection.MostBoughtProductProjection;
import com.register.model.projection.PriceStatsProjection;
import com.register.registers.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findByUserUserId(Long userId);

        @Query("SELECT p FROM Product p WHERE p.productId IN :ids")
        List<Product> findAllByIdIn(@Param("ids") Collection<Long> ids);

        Page<Product> findByUserUserIdAndNameContainingIgnoreCase(Long userId, String name, Pageable pageable);

        @Query(value = """
                SELECT   average_price,most_expensive from      (
                SELECT AVG(price) AS average_price
                FROM Product
                WHERE user_id = :user_id
        ),        (
                SELECT MAX(price) AS most_expensive
                FROM Product
                WHERE user_id = :user_id
        )
        """, nativeQuery = true)
        PriceStatsProjection findPriceStats(@Param("user_id") Long user_id);

        @Query("""
                        SELECT p.name As name, COUNT(pu.product) AS totalProduct
                        FROM Purchases pu
                        JOIN pu.product p
                        WHERE pu.user.id = :userId
                        GROUP BY p.id
                        ORDER BY totalProduct DESC
                        LIMIT 1
                        """)
        MostBoughtProductProjection findMostPurchasedProduct(@Param("userId") Long userId);

}
