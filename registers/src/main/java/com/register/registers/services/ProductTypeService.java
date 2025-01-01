package com.register.registers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.register.registers.dto.ProductTypeRequestDTO;
import com.register.registers.entities.ProductType;
import com.register.registers.entities.Users;
import com.register.registers.repositories.ProductTypeRepository;
@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired 
    private UserService userService;
    public List<ProductType> getProductType() {
        return productTypeRepository.findAll();
    }
    public ProductType addProductType(ProductTypeRequestDTO productTypeDTO) {
        Users user = userService.findUserById(productTypeDTO.getUserId());
        ProductType productType = new ProductType();
        productType.setUser(user);
        productType.setName(productTypeDTO.getName());
        productType.setDescription(productTypeDTO.getDescription());

        return productTypeRepository.save(productType);
    }
    public List<ProductType> getProductTypesUser(Long id_user) {
        return productTypeRepository.findByUserUserId(id_user);
    }
}