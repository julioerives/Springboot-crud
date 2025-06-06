package com.register.registers.services.products;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.register.model.projection.MostBoughtProductProjection;
import com.register.model.projection.PriceStatsProjection;
import com.register.registers.dto.ProductRequestDTO;
import com.register.registers.dto.ProductStatsDTO;
import com.register.registers.dto.ProductsResponseDTO;
import com.register.registers.entities.Product;
import com.register.registers.entities.ProductType;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.mappers.ProductMapper;
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
    @Autowired
    private ProductMapper productMapper;

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

    public List<Product> getProductsByUser(HttpServletRequest request) {
        Long id_user = userTokenService.getCurrentUserId(request);
        userService.findUserById(id_user);
        return productRepository.findByUserUserId(id_user);
    }

    public Product getProducyById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado."));
    }

    public List<Product> findAllByIdIn(Collection<Long> ids){
        List<Product> products = this.productRepository.findAllByIdIn(ids);
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Productos no existen");
        }
        return products;
    }

    public Page<ProductsResponseDTO> getProductsPage(HttpServletRequest request, String name, int page, int size){
        Long userIdLong = userTokenService.getCurrentUserId(request);
        Page<Product> products = productRepository.findByUserUserIdAndNameContainingIgnoreCase(userIdLong, name, PageRequest.of(page, size));
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Productos no encontrados");
        }
        return products.map(productMapper::toDto);

    }

    public ProductStatsDTO getProductsStats(HttpServletRequest request) {
        Long userId = userTokenService.getCurrentUserId(request);
        PriceStatsProjection priceStats = productRepository.findPriceStats(userId);

        MostBoughtProductProjection topProducts = productRepository.findMostPurchasedProduct(userId);
        ProductStatsDTO productStatsDTO = new ProductStatsDTO(priceStats, topProducts);
        return productStatsDTO;
    }
}
