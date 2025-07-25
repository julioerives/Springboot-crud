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

import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.IncomeTypeRequestDTO;
import com.register.registers.entities.postgres.IncomeType;
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
    public ResponseEntity<Response<IncomeType>> addIncomeType(@RequestBody IncomeTypeRequestDTO iRequestDTO) {
        IncomeType incomeType = incomeTypeService.addIncomeType(iRequestDTO);
        return responseService.buildSuccessResponse(incomeType, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Response<List<IncomeType>>> getIncomeType(@PathVariable Long userId) {
        List<IncomeType> incomeTypes = incomeTypeService.getIncomeTypes(userId);
        return responseService.buildSuccessResponse(incomeTypes, SuccessResponse.SUCCESS_GET, HttpStatus.OK);

    }
}
