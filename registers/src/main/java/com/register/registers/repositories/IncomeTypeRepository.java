package com.register.registers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.IncomeType;

public interface IncomeTypeRepository extends JpaRepository<IncomeType,Long>{
}
