package com.register.registers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeRequestDTO {
    @NotNull(message = "EL nombre del tipo de producto es requerido")
    private String name;
    @NotNull(message = "La descripcion del tipo de producto es requerido")
    private String description;
    @NotNull(message = "La descripcion del tipo de producto es requerido")
    private String colorRgba;
}
