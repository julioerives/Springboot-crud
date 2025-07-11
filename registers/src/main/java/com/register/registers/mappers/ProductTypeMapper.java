package com.register.registers.mappers;

import org.mapstruct.Mapper;

import com.register.registers.dto.ProductTypeRequestDTO;
import com.register.registers.entities.ProductType;
import com.register.registers.entities.Users;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductType toEntity(ProductTypeRequestDTO productTypeRequestDTO, Users user);
}
