package com.register.registers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.ErrorMessages;
import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.ProductRequestDTO;
import com.register.registers.entities.Product;
import com.register.registers.interfaces.Response;
import com.register.registers.services.products.ProductService;
import com.register.registers.services.utils.ResponseService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ResponseService responseService;

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<Response<Product>> addProduct(@RequestBody ProductRequestDTO productDTO, HttpServletRequest request) {
        Product product = productService.addProduct(productDTO, request);
        return responseService.buildSuccessResponse(product, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);
    }

    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<Response<List<Product>>> getProductsByUser(@PathVariable Long userId) {
        List<Product> product = productService.getProductsByUser(userId);
        if (product.size() < 1) {
            return responseService.buildErrorResponse(ErrorMessages.NO_DATA_FOUND, HttpStatus.NOT_FOUND);
        }
        return responseService.buildSuccessResponse(product, SuccessResponse.SUCCESS_GET, HttpStatus.OK);

    }
}
