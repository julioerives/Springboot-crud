package com.register.registers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.register.registers.dto.IncomeTypeRequestDTO;
import com.register.registers.entities.IncomeType;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.repositories.IncomeTypeRepository;

@Service
public class IncomeTypeService {
    @Autowired
    private UserService userService;
    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    public IncomeType addIncomeType(IncomeTypeRequestDTO iDto){
        Users userFound = userService.findUserById(iDto.getUserId());
        IncomeType incomeType = new IncomeType();
        incomeType.setUser(userFound);
        incomeType.setDescription(iDto.getDescription());
        incomeType.setName(iDto.getName());
        incomeType.setColorRgba(iDto.getColorRgba());
        return incomeTypeRepository.save(incomeType);
    }
    public List<IncomeType> getIncomeTypes(Long userId){
        return incomeTypeRepository.findByUserUserId(userId).orElseThrow(()-> new ResourceNotFoundException("Tipo de ingreso no encontrado"));
    }
    public IncomeType getIncomeTypeByIdAndIdUser(Long userId, Long incomeTypeId){
        return incomeTypeRepository.findByUserUserIdAndIncomeTypeId(userId, incomeTypeId).orElseThrow(()-> new ResourceNotFoundException("Tipo de ingreso no encontrado")); 
    }
}
