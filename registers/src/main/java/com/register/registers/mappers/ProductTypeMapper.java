package com.register.registers.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.register.registers.dto.ProductTypeRequestDTO;
import com.register.registers.dto.ProductTypeResponseDTO;
import com.register.registers.entities.ProductType;
import com.register.registers.entities.Users;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    @Mapping(target = "productTypeId", ignore = true)
    @Mapping(target = "products", ignore = true)
    ProductType toEntity(ProductTypeRequestDTO productTypeRequestDTO, Users user);
    List<ProductTypeResponseDTO> tDto(List<ProductType> productType);
}