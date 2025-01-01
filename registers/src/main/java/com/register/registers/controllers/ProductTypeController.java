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
import com.register.registers.dto.ProductTypeRequestDTO;
import com.register.registers.entities.ProductType;
import com.register.registers.exceptions.UsersExceptions.UserNotFoundException;
import com.register.registers.interfaces.Response;
import com.register.registers.services.ProductTypeService;
import com.register.registers.services.ResponseService;

@RestController
@RequestMapping("/type_products")
public class ProductTypeController {
    @Autowired
    private ResponseService responseService;
    @Autowired
    private ProductTypeService productTypeService;
    @PostMapping("")
    public ResponseEntity<Response<ProductType>> addProductType(@RequestBody ProductTypeRequestDTO productTypeBody){
        try{
            ProductType productType = productTypeService.addProductType(productTypeBody);
            return responseService.buildSuccessResponse(productType, SuccessResponse.SUCCESS_POST, HttpStatus.OK);
        }catch(UserNotFoundException e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/byUser/{id_user}")
    public ResponseEntity<Response<List<ProductType>>> getProductsTypeByUser(@PathVariable Long id_user){
        try{
            List<ProductType> response = productTypeService.getProductTypesUser(id_user);
            if(response.size() < 1){
                return responseService.buildErrorResponse(ErrorMessages.NO_DATA_FOUND,HttpStatus.NOT_FOUND);
            }
            return responseService.buildSuccessResponse(response,SuccessResponse.SUCCESS_GET,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
