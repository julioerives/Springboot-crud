package com.register.registers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.ProductTypeRequestDTO;
import com.register.registers.dto.ProductTypeResponseDTO;
import com.register.registers.entities.postgres.ProductType;
import com.register.registers.interfaces.Response;
import com.register.registers.services.products.ProductTypeService;
import com.register.registers.services.utils.ResponseService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/type_products")
public class ProductTypeController {
    @Autowired
    private ResponseService responseService;
    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping("")
    public ResponseEntity<Response<ProductType>> addProductType(@RequestBody ProductTypeRequestDTO productTypeBody, HttpServletRequest request) {
        ProductType productType = productTypeService.addProductType(productTypeBody, request);
        return responseService.buildSuccessResponse(productType, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Response<List<ProductTypeResponseDTO>>> getProductsTypeByUser(HttpServletRequest request) {
        List<ProductTypeResponseDTO> response = productTypeService.getProductTypesUser(request);
        return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);

    }
}
