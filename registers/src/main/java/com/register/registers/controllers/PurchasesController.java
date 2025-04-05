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
import com.register.registers.dto.PurchasesRequestDTO;
import com.register.registers.entities.Purchases;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.interfaces.Response;
import com.register.registers.services.purchases.PurchasesService;
import com.register.registers.services.utils.ResponseService;

@RequestMapping("/purchases")
@RestController
public class PurchasesController {
    @Autowired
    private PurchasesService purchasesService;
    @Autowired
    private ResponseService responseService;
    @PostMapping("")
    public ResponseEntity<Response<Purchases>> addPurchases(@RequestBody PurchasesRequestDTO pRequestDTO){
        try{
            Purchases purchases = purchasesService.newPurchase(pRequestDTO);
            return responseService.buildSuccessResponse(purchases,SuccessResponse.SUCCESS_POST, HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{idUser}")
    public ResponseEntity<Response<List<Purchases>>> getPurchases(@PathVariable Long idUser){
        try{
            List<Purchases> purchases = purchasesService.getPurchases(idUser);
            if(purchases.size() < 1){
                return responseService.buildErrorResponse(ErrorMessages.NO_DATA_FOUND, HttpStatus.NOT_FOUND);
            }
            return responseService.buildSuccessResponse(purchases, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
