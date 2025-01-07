package com.register.registers.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Purchases;

public interface PurchasesRepositoy extends JpaRepository<Purchases,Long>  {
    public Optional<List<Purchases>>  findByUserUserId(Long userId); 
}
