package com.register.registers.dto;

import java.time.LocalDate;

import com.register.registers.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasesResponseDTO {
    private Long purchaseId;
    private Product product;
    private int quantity;
    private LocalDate purchaseDate;
    private Float price;
    private String description;
}
