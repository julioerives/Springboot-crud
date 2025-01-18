package com.register.registers.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Income;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface IncomeRepository extends JpaRepository<Income,Long> {
    Page<Income> findByUserUserIdAndDateBetween(Long userId,LocalDate startDate,LocalDate endDate,Pageable pageable );
}