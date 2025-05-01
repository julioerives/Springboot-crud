package com.register.registers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    @NotNull(message = "El tipo del producto es requerido")
    private Long typeProductId;
    @NotNull(message = "El nombre del producto es requerdio")
    private String name;
    @NotNull(message = "El precio del producto es requerido")
    private Float price;
}
