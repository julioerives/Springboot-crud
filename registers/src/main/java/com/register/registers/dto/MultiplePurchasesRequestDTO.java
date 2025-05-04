package com.register.registers.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiplePurchasesRequestDTO {
    @NotEmpty(message = "Las compras deber de ser requeridas")
    @Valid
    private List<PurchaseRequestDTO> items;
}
