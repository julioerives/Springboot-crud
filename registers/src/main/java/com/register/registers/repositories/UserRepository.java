package com.register.registers.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Users;

public interface UserRepository extends JpaRepository<Users,Long> {
    // Optional<Users> findByEmailAndPasswordHash(String email, String password_hash);
    Optional<Users> findByEmail(String email);
}
