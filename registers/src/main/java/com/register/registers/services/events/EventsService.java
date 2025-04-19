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
import com.register.registers.services.jwtServices.JWTService;
import com.register.registers.services.users.UserService;
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

    public Page<Events> getAllEvents(int page, int size, HttpServletRequest request) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("El número de página y el tamaño deben ser mayores que cero.");
        }
        String token = cookiesService.getCookie(request, "token");
        Long userId = jwtService.extractUserId(token);
        userService.findUserById(userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());

        Page<Events> events = this.eventsRepository.findByUserUserId(userId, pageable);
        if (events.isEmpty()) {
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
