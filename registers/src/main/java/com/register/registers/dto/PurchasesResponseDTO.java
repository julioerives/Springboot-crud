package com.register.registers.dto;

import java.time.LocalDate;

import com.register.registers.entities.postgres.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchasesResponseDTO {
    private Long purchase_id;
    private Product product;
    private int quantity;
    private LocalDate purchaseDate;
    private Float price;
    private String description;
}
