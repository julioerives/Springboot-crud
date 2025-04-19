package com.register.registers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.Events;

public interface EventsRepository extends JpaRepository<Events, Long> {   
}
