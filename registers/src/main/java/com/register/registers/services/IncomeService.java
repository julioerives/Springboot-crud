package com.register.registers.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.register.registers.dto.IncomeRequestDTO;
import com.register.registers.entities.Income;
import com.register.registers.entities.IncomeType;
import com.register.registers.entities.Users;
import com.register.registers.repositories.IncomeRepository;

public class IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private IncomeTypeService iTypeService;
    @Autowired
    private UserService userService;
    public Income newIncome(IncomeRequestDTO incomeRequestDTO){
        Users user = userService.findUserById(incomeRequestDTO.getUserId());
        IncomeType incomeType = iTypeService.getIncomeTypeByIdAndIdUser(incomeRequestDTO.getUserId(),incomeRequestDTO.getIncomeTypeId() );
        Income income = new Income();
        income.setIncomeType(incomeType);
        income.setQuantity(incomeRequestDTO.getQuantity());
        income.setDescription(income.getDescription());
        income.setUser(user);
        return incomeRepository.save(income);
    }
}
