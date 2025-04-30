package com.register.registers.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeRequestDTO {
    private Long userId;
    private Long incomeTypeId;
    private Float quantity;
    private String description;
    private LocalDate date;
}
