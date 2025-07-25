package com.register.registers.services.events;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.register.registers.dto.EventsDTO;
import com.register.registers.entities.mongo.Events;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.exceptions.defaultExceptions.UnauthorizedActionException;
import com.register.registers.mappers.EventsMapper;
import com.register.registers.repositories.mongo.EventsRepository;
import com.register.registers.services.jwtServices.JWTService;
import com.register.registers.services.users.UserService;
import com.register.registers.services.users.UserTokenService;
import com.register.registers.services.utils.CookiesService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EventsService {
    @Autowired
    EventsRepository eventsRepository;
    @Autowired
    CookiesService cookiesService;
    @Autowired
    JWTService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    UserTokenService userTokenService;
    @Autowired
    EventsMapper eventsMapper;


    public Page<Events> getAllEvents(int page, int size, HttpServletRequest request) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("El número de página y el tamaño deben ser mayores que cero.");
        }
        Long userId = userTokenService.getCurrentUserId(request);
        Pageable pageable = PageRequest.of(page, size);

        Page<Events> events = this.eventsRepository.findByUserId(userId, pageable);
        if (events.isEmpty()) {
            throw new ResourceNotFoundException("Eventos no encontrados");
        }
        return events;
    }

    public Events findEventById(String eventId) {
        Events event = this.eventsRepository.findById(eventId)
        .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));
        return event;
    }

    public Events addEvent(EventsDTO eventsDTO, HttpServletRequest request) {
        Long userId = userTokenService.getCurrentUserId(request);
        Events event = eventsMapper.toEntity(eventsDTO, userId);
        return this.eventsRepository.save(event);
    }

    public void deleteEvent(String eventId, HttpServletRequest request) {
        Long userId = userTokenService.getCurrentUserId(request);
        Events event = findEventById(eventId);
        if (event.getUserId() != userId) {
            throw new UnauthorizedActionException("No tienes permiso para eliminar este evento");
        }
        this.eventsRepository.delete(event);
    }

    public Events updateEvent(String eventId, EventsDTO eventsDTO, HttpServletRequest request) {
        Long userId = userTokenService.getCurrentUserId(request);
        Events event = findEventById(eventId);
        if (event.getUserId() != userId) {
            throw new UnauthorizedActionException("No tienes permiso para actualizar este evento");
        }
        event.setEventName(eventsDTO.getEventName());
        event.setStartDate(eventsDTO.getStartDate());
        event.setEndDate(eventsDTO.getEndDate());
        event.setPhoneNotifications(eventsDTO.getPhoneNotifications());
        event.setWebNotifications(eventsDTO.getWebNotifications());
        event.setMinutesAdvice(eventsDTO.getMinutesAdvice());
        return this.eventsRepository.save(event);
    }

    public List<Events> getEventsByDates(LocalDateTime startDate, LocalDateTime endDate, HttpServletRequest request) {
        Long userId = userTokenService.getCurrentUserId(request);
        List<Events> event = this.eventsRepository.findByUserIdAndStartDateBetween(userId, startDate, endDate);
        if (event.isEmpty()) {
            throw new ResourceNotFoundException("Eventos no encontrados");
        }
        return event;
    }
}
