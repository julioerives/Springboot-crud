package com.register.registers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeRequestDTO {
    private Long userId;
    private String name;
    private String description;
    private String colorRgba;
}
