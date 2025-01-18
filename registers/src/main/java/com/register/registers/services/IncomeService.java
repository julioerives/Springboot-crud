package com.register.registers.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.register.registers.dto.IncomeRequestDTO;
import com.register.registers.entities.Income;
import com.register.registers.entities.IncomeType;
import com.register.registers.entities.Users;
import com.register.registers.repositories.IncomeRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
@Service
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
        income.setDate(incomeRequestDTO.getDate());
        return incomeRepository.save(income);
    }
    public Page<Income> getIncomesByIdUser(Long userId,Date startDate,Date endDate, int page, int size) {
        userService.findUserById(userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());
        return incomeRepository.findByUserUserIdAndDateBetween(userId,startDate,endDate,pageable);
    }
}
