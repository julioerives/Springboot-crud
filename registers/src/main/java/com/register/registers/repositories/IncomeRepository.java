package com.register.registers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Income;

public interface IncomeRepository extends JpaRepository<Income,Long> {
}