package com.register.registers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.ErrorMessages;
import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.IncomeRequestDTO;
import com.register.registers.entities.Income;
import com.register.registers.entities.IncomeType;
import com.register.registers.interfaces.Response;
import com.register.registers.services.IncomeService;
import com.register.registers.services.ResponseService;

@RestController
@RequestMapping("/income")
public class IncomeController {
    @Autowired 
    private ResponseService responseService;
    @Autowired 
    private IncomeService incomeService;
    @PostMapping("")
    public ResponseEntity<Response<Income>> addIncome(@RequestBody IncomeRequestDTO iRequestDTO){
        try{
            Income income = incomeService.newIncome(iRequestDTO);
            return responseService.buildSuccessResponse(income, SuccessResponse.SUCCESS_POST, HttpStatus.OK);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
