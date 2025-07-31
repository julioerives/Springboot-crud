package com.register.registers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.register.registers.dto.EventsDTO;
import com.register.registers.entities.mongo.Events;

@Mapper(componentModel = "spring")
public interface EventsMapper {
    @Mapping(target = "exDates", ignore = true)
    @Mapping(target = "active", ignore = true)
    Events toEntity(EventsDTO eventsDTO, Long userId);
}
