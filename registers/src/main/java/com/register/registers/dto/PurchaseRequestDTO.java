package com.register.registers.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDTO {
    @NotNull(message = "El ID de producto es requerido")
    private Long productId;
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int quantity;
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Float price;
}
