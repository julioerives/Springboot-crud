package com.register.registers.services.incomes;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.register.registers.dto.IncomeRequestDTO;
import com.register.registers.entities.Income;
import com.register.registers.entities.IncomeType;
import com.register.registers.entities.Users;
import com.register.registers.repositories.IncomeRepository;
import com.register.registers.services.users.UserService;

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
    public Page<Income> getIncomesByIdUser(Long userId,LocalDate startDate,LocalDate endDate, int page, int size) {
        userService.findUserById(userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());
        Page<Income> result = incomeRepository.findByUserUserIdAndDateBetween(userId, startDate, endDate, pageable);
        return result;
    }
}
