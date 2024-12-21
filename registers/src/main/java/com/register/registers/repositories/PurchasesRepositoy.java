package com.register.registers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Purchases;

public interface PurchasesRepositoy extends JpaRepository<Purchases,Long>  {
}
