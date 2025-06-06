package com.register.registers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.ErrorMessages;
import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.PageDTOResponse;
import com.register.registers.dto.ProductRequestDTO;
import com.register.registers.dto.ProductStatsDTO;
import com.register.registers.dto.ProductsResponseDTO;
import com.register.registers.entities.Product;
import com.register.registers.interfaces.Response;
import com.register.registers.services.products.ProductService;
import com.register.registers.services.utils.ResponseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ResponseService responseService;

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<Response<Product>> addProduct(@RequestBody @Valid ProductRequestDTO productDTO,
            HttpServletRequest request) {
        Product product = productService.addProduct(productDTO, request);
        return responseService.buildSuccessResponse(product, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Response<List<Product>>> getProductsByUser(HttpServletRequest request) {
        List<Product> product = productService.getProductsByUser(request);
        if (product.size() < 1) {
            return responseService.buildErrorResponse(ErrorMessages.NO_DATA_FOUND, HttpStatus.NOT_FOUND);
        }
        return responseService.buildSuccessResponse(product, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Response<PageDTOResponse<ProductsResponseDTO>>> getProductsPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", defaultValue = "") String name,
            HttpServletRequest request) {
        Page<ProductsResponseDTO> products = productService.getProductsPage(request, name, page, size);
        PageDTOResponse<ProductsResponseDTO> response = PageDTOResponse.of(products);
        return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);

    }

    @GetMapping("/stats")
    public ResponseEntity<Response<ProductStatsDTO>> getProductStats(
            HttpServletRequest request) {
        ProductStatsDTO response = productService.getProductsStats(request);
        return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }
}
