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
import com.register.registers.exceptions.UsersExceptions.UserNotFoundException;
import com.register.registers.exceptions.producTypeExceptions.ProductTypeNotFound;
import com.register.registers.interfaces.Response;
import com.register.registers.services.ProductService;
import com.register.registers.services.ResponseService;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ResponseService responseService;

    @Autowired
    private ProductService productService;
    @PostMapping("")
    public ResponseEntity<Response<Product>> addProduct(@RequestBody ProductRequestDTO productDTO) {
        if (productDTO.getUserId() == null || productDTO.getTypeProductId() == null || productDTO.getName() == null) {
            return responseService.buildErrorResponse("Missing required fields", HttpStatus.BAD_REQUEST);
        }

        try{
            Product product = productService.addProduct(productDTO);
            return responseService.buildSuccessResponse(product, SuccessResponse.SUCCESS_POST, HttpStatus.OK);
        }catch(UserNotFoundException e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(ProductTypeNotFound e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<Response<List<Product>>> getProductsByUser(@PathVariable Long userId){
        try{
            List<Product> product = productService.getProductsByUser(userId);
            if(product.size() < 1){
                return responseService.buildErrorResponse(ErrorMessages.NO_DATA_FOUND, HttpStatus.NOT_FOUND);
            }
            return responseService.buildSuccessResponse(product, SuccessResponse.SUCCESS_GET,HttpStatus.OK);
        }catch(UserNotFoundException e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
