package com.register.registers.mappers;

import org.mapstruct.Mapper;

import com.register.registers.dto.EventsDTO;
import com.register.registers.entities.mongo.Events;

@Mapper(componentModel = "spring")
public interface EventsMapper {
    Events toEntity(EventsDTO eventsDTO, Long userId);

}
