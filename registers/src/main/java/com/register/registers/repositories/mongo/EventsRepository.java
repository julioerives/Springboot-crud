package com.register.registers.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.register.registers.entities.mongo.Events;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventsRepository extends MongoRepository<Events, String> {
    Page<Events> findByUserId(Long userId, Pageable pageable);


    List<Events> findByUserIdAndStartDateBetween(Long userId, LocalDateTime start, LocalDateTime end);

}
