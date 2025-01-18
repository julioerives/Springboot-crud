package com.register.registers.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Income;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface IncomeRepository extends JpaRepository<Income,Long> {
    Page<Income> findByUserUserIdAndDateBetween(Long userId,Date startDate,Date endDate,Pageable pageable );
}