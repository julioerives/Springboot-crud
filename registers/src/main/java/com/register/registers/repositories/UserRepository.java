package com.register.registers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Users;

public interface UserRepository extends JpaRepository<Users,Long> {

}