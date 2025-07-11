package com.register.registers.services.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.register.registers.dto.ProductTypeRequestDTO;
import com.register.registers.entities.ProductType;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.producTypeExceptions.ProductTypeNotFound;
import com.register.registers.mappers.ProductTypeMapper;
import com.register.registers.repositories.ProductTypeRepository;
import com.register.registers.services.users.UserTokenService;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    ProductTypeMapper productTypeMapper;

    public List<ProductType> getProductType() {
        return productTypeRepository.findAll();
    }
    public ProductType addProductType(ProductTypeRequestDTO productTypeDTO, HttpServletRequest request) {
        Users user = userTokenService.getCurrentUser(request);
        ProductType productType = productTypeMapper.toEntity(productTypeDTO, user);
        return productTypeRepository.save(productType);
    }
    public List<ProductType> getProductTypesUser(Long id_user) {
        return productTypeRepository.findByUserUserId(id_user);
    }
    public List<ProductType> getProductTypesUser(HttpServletRequest request) {
        Long id_user = userTokenService.getCurrentUserId(request);

        return productTypeRepository.findByUserUserId(id_user);
    }
    public ProductType getProductTypeByIdAndUser(Long productTypeId,Long id_user) {
        return productTypeRepository.findByProductTypeIdAndUserUserId(productTypeId,id_user).orElseThrow(()-> new ProductTypeNotFound("Tipo de producto no encontrado."));
    }
}
