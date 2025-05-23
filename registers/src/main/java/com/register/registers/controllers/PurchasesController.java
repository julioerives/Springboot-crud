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

import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.MultiplePurchasesRequestDTO;
import com.register.registers.dto.PurchaseRequestDTO;
import com.register.registers.dto.PurchasesResponseDTO;
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
    public ResponseEntity<Response<Purchases>> addPurchases(@RequestBody PurchaseRequestDTO pRequestDTO,
            HttpServletRequest request) {
        Purchases purchases = purchasesService.addPurchases(pRequestDTO, request);
        return responseService.buildSuccessResponse(purchases, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);

    }

    @PostMapping("/multiple")
    public ResponseEntity<Response<List<Purchases>>> addMultiplePurchases(
            @RequestBody @Valid MultiplePurchasesRequestDTO pRequestDTO, HttpServletRequest request) {
        List<Purchases> purchases = purchasesService.addMultiplePurchase(pRequestDTO, request);
        return responseService.buildSuccessResponse(purchases, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);

    }

    @GetMapping("")
    public ResponseEntity<Response<Page<PurchasesResponseDTO>>> getPurchases(
            @RequestParam("sort") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam("searchBy") String searchBy,
            HttpServletRequest request) {
        Page<PurchasesResponseDTO> purchases = purchasesService.getPurchases(request, sort, page, size, name, searchBy);
        return responseService.buildSuccessResponse(purchases, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }
}
