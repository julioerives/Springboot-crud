package com.register.registers.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.register.registers.dto.IncomeRequestDTO;
import com.register.registers.entities.Income;
import com.register.registers.exceptions.UsersExceptions.UserNotFoundException;
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
    @GetMapping("")
    public ResponseEntity<Response<Page<Income>>> getIncomes(
        @RequestParam("userId") Long userId,
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    ){

        try{
            Page<Income> response = incomeService.getIncomesByIdUser(userId, startDate, endDate, page, size);
            return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
        }catch(UserNotFoundException e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse( "Usuario no encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
