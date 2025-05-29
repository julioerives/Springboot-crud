package com.register.registers.mappers;

import org.mapstruct.Mapper;

import com.register.registers.dto.ProductsResponseDTO;
import com.register.registers.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductsResponseDTO toDto(Product product);
}
