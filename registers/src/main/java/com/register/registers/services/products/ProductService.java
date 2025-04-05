package com.register.registers.services.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.register.registers.dto.ProductRequestDTO;
import com.register.registers.entities.Product;
import com.register.registers.entities.ProductType;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.repositories.ProductRepository;
import com.register.registers.services.users.UserService;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductTypeService productTypeService;
    @Autowired
    UserService userService;

    public Product addProduct(ProductRequestDTO productDTO) {
        Users user = userService.findUserById(productDTO.getUserId());
        ProductType productType = productTypeService.getProductTypeByIdAndUser(productDTO.getTypeProductId(),
                productDTO.getUserId());
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setProductType(productType);
        product.setUser(user);
        return productRepository.save(product);
    }

    public List<Product> getProductsByUser(Long userId) {
        userService.findUserById(userId);
        return productRepository.findByUserUserId(userId);
    }

    public Product getProducyById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado."));
    }
}
