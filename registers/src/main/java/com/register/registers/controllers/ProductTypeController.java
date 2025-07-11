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

import com.register.registers.constants.ErrorMessages;
import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.ProductTypeRequestDTO;
import com.register.registers.entities.ProductType;
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

    @GetMapping("/byUser")
    public ResponseEntity<Response<List<ProductType>>> getProductsTypeByUser(HttpServletRequest request) {
        List<ProductType> response = productTypeService.getProductTypesUser(request);
        if (response.size() < 1) {
            return responseService.buildErrorResponse(ErrorMessages.NO_DATA_FOUND, HttpStatus.NOT_FOUND);
        }
        return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);

    }
}
