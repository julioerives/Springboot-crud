package com.register.registers.dto;

import com.register.registers.entities.ProductType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponseDTO {
    private Long productId;
    private ProductType productType;
    private Float price;
    private String name;
    private String createdAt;
}
