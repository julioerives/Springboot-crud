package com.register.registers.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasesRequestDTO {
    private List<PurchaseItemDTO> items;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseItemDTO {
        private Long productId;
        private int quantity;
        private Float price;
    }
}
