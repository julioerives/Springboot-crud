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
import com.register.registers.dto.IncomeTypeRequestDTO;
import com.register.registers.entities.IncomeType;
import com.register.registers.exceptions.UsersExceptions.UserNotFoundException;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.interfaces.Response;
import com.register.registers.services.incomes.IncomeTypeService;
import com.register.registers.services.utils.ResponseService;

@RestController
@RequestMapping("/incomeType")
public class IncomeTypeController {
    @Autowired
    private ResponseService responseService;
    @Autowired
    private IncomeTypeService incomeTypeService;

    @PostMapping("")
    public ResponseEntity<Response<IncomeType>> addIncomeType(@RequestBody IncomeTypeRequestDTO iRequestDTO){
        try{
            IncomeType incomeType = incomeTypeService.addIncomeType(iRequestDTO);
            return responseService.buildSuccessResponse(incomeType, SuccessResponse.SUCCESS_POST, HttpStatus.OK);
        }catch(UserNotFoundException e){
            return responseService.buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Response<List<IncomeType>>> getIncomeType(@PathVariable Long userId){
        try{
            List<IncomeType> incomeTypes = incomeTypeService.getIncomeTypes(userId);
            return responseService.buildSuccessResponse(incomeTypes, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
        }catch(UserNotFoundException e){
            return responseService.buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(ResourceNotFoundException e){
            return responseService.buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
