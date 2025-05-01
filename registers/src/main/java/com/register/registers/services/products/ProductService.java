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
import com.register.registers.services.users.UserTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductTypeService productTypeService;
    @Autowired
    UserService userService;
    @Autowired
    UserTokenService userTokenService;

    public Product addProduct(ProductRequestDTO productDTO, HttpServletRequest request) {
        Users user = userTokenService.getCurrentUser(request);
        ProductType productType = productTypeService.getProductTypeByIdAndUser(productDTO.getTypeProductId(),
                user.getUserId());
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
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
