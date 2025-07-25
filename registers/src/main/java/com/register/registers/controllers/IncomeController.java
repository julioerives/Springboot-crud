package com.register.registers.controllers;

import java.time.LocalDate;

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

import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.IncomeRequestDTO;
import com.register.registers.dto.PageDTOResponse;
import com.register.registers.entities.postgres.Income;
import com.register.registers.interfaces.Response;
import com.register.registers.services.incomes.IncomeService;
import com.register.registers.services.utils.ResponseService;

@RestController
@RequestMapping("/income")
public class IncomeController {
    @Autowired
    private ResponseService responseService;
    @Autowired
    private IncomeService incomeService;

    @PostMapping("")
    public ResponseEntity<Response<Income>> addIncome(@RequestBody IncomeRequestDTO iRequestDTO) {
        Income income = incomeService.newIncome(iRequestDTO);
        return responseService.buildSuccessResponse(income, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Response<PageDTOResponse<Income>>> getIncomes(
            @RequestParam("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Page<Income> pageIncome = incomeService.getIncomesByIdUser(userId, startDate, endDate, page, size);
        PageDTOResponse<Income> response = PageDTOResponse.of(pageIncome);
        return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }
}
