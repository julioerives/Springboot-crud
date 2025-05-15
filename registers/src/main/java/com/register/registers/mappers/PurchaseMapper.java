package com.register.registers.mappers;

import org.mapstruct.Mapper;

import com.register.registers.dto.PurchasesResponseDTO;
import com.register.registers.entities.Purchases;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    PurchasesResponseDTO toDto(Purchases entity);
}
