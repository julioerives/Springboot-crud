package com.register.registers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasesRequestDTO {
    private Long userId;
    private Long productId;
    private int quantity;
    private Float price;
}
