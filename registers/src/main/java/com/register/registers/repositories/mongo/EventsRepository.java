package com.register.registers.repositories.mongo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.register.registers.entities.mongo.Events;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventsRepository extends JpaRepository<Events, Long> {
    Page<Events> findByUserUserId(Long userId, Pageable pageable);

    List<Events> findByUserUserIdAndStartDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

}
