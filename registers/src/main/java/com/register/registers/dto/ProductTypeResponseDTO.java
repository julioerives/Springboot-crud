package com.register.registers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeResponseDTO {
    private Long productTypeId;
    private String name;
    private String description;
    private String colorRgba;

}
