package com.register.registers.services.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.register.registers.dto.EventsDTO;
import com.register.registers.entities.Events;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.repositories.EventsRepository;

@Service
public class EventsService {
    @Autowired
    EventsRepository eventsRepository;
    public Page<Events> getAllEvents(int page, int size) {
        if(page < 0 || size <= 0) {
            throw new IllegalArgumentException("El número de página y el tamaño deben ser mayores que cero.");
        }
                Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());

        Page<Events> events = this.eventsRepository.findAll(pageable);
        if(events.isEmpty()) {
            throw new ResourceNotFoundException("Eventos no encontrados");
        }
        return events;
    }

    public Events addEvent(EventsDTO eventsDTO) {
        Events event = new Events();
        event.setStartDate(eventsDTO.getStartDate());
        event.setEndDate(eventsDTO.getEndDate());
        event.setPhoneNotifications(eventsDTO.getPhoneNotifications());
        event.setWebNotificacions(eventsDTO.getWebNotificacions());
        event.setMinutesAdvice(eventsDTO.getMinutesAdvice());
        return this.eventsRepository.save(event);
    }
}
