package com.register.registers.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventsRepository extends JpaRepository<Events, Long> {
    Page<Events> findByUserUserId(Long userId, Pageable pageable);   
}
