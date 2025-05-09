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
import com.register.registers.dto.MultiplePurchasesRequestDTO;
import com.register.registers.dto.PurchaseRequestDTO;
import com.register.registers.entities.Purchases;
import com.register.registers.interfaces.Response;
import com.register.registers.services.purchases.PurchasesService;
import com.register.registers.services.utils.ResponseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/purchases")
@RestController
public class PurchasesController {
    @Autowired
    private PurchasesService purchasesService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("")
    public ResponseEntity<Response<Purchases>> addPurchases(@RequestBody PurchaseRequestDTO pRequestDTO, HttpServletRequest request) {
        Purchases purchases = purchasesService.addPurchases(pRequestDTO, request);
        return responseService.buildSuccessResponse(purchases, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);

    }
    @PostMapping("/multiple")
    public ResponseEntity<Response<List<Purchases>>> addMultiplePurchases(@RequestBody @Valid MultiplePurchasesRequestDTO pRequestDTO, HttpServletRequest request) {
        List<Purchases> purchases = purchasesService.addMultiplePurchase(pRequestDTO, request);
        return responseService.buildSuccessResponse(purchases, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);

    }

    @GetMapping("")
    public ResponseEntity<Response<List<Purchases>>> getPurchases(HttpServletRequest request) {
        List<Purchases> purchases = purchasesService.getPurchases(request);
        return responseService.buildSuccessResponse(purchases, SuccessResponse.SUCCESS_GET, HttpStatus.OK);

    }
}
