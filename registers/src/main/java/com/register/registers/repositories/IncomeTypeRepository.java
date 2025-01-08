package com.register.registers.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.IncomeType;

public interface IncomeTypeRepository extends JpaRepository<IncomeType,Long>{
            Optional<List<IncomeType>> findByUserUserId(Long userId);
}
