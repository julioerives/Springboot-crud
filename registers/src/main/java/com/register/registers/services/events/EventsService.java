package com.register.registers.services.events;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.register.registers.dto.EventInstance;
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

    public Page<EventInstance> getAllEvents(int page, int size, LocalDate startDate, LocalDate endDate,
            HttpServletRequest request) {
        validatePagination(page, size);

        Long userId = userTokenService.getCurrentUserId(request);
        Pageable pageable = PageRequest.of(page, size);

        LocalDate today = LocalDate.now();
        LocalDate rangeStart = startDate != null ? startDate : today;
        LocalDate rangeEnd = endDate != null ? endDate : today.plusMonths(1);

        List<Events> allEvents = eventsRepository.findByUserId(userId);
        List<EventInstance> expandedEvents = expandAllEvents(allEvents, rangeStart, rangeEnd);

        return getPaginatedResult(expandedEvents, pageable);
    }

    private void validatePagination(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Página y tamaño deben ser mayores a cero");
        }
    }

    private List<EventInstance> expandAllEvents(List<Events> events, LocalDate rangeStart, LocalDate rangeEnd) {
        List<EventInstance> instances = new ArrayList<>();

        for (Events event : events) {
            if (Boolean.TRUE.equals(event.getIsRecurrent())) {
                instances.addAll(expandRecurrentEvent(event, rangeStart, rangeEnd));
                continue;
            }

            if (isWithinRange(event.getStartDate().toLocalDate(), rangeStart, rangeEnd)) {
                instances.add(new EventInstance(event, event.getStartDate()));
            }
        }

        return instances;
    }

    private List<EventInstance> expandRecurrentEvent(Events event, LocalDate rangeStart, LocalDate rangeEnd) {
        List<EventInstance> instances = new ArrayList<>();
        Set<LocalDateTime> exDates = Optional.ofNullable(event.getExDates()).orElse(Set.of());
        List<Integer> daysOfWeek = Optional.ofNullable(event.getDaysOfWeek()).orElse(List.of());

        if (daysOfWeek.isEmpty())
            return instances;

        LocalDateTime eventStart = event.getStartDate();
        LocalDateTime adjustedEnd = adjustEndDate(event.getEndDate(), rangeEnd);

        for (LocalDate date = rangeStart; !date.isAfter(adjustedEnd.toLocalDate()); date = date.plusDays(1)) {
            int dayValue = date.getDayOfWeek().getValue();

            if (!daysOfWeek.contains(dayValue))
                continue;

            LocalDateTime startDate = date.atTime(eventStart.toLocalTime());

            if (!exDates.contains(startDate)) {
                instances.add(new EventInstance(event, startDate));
            }
        }

        return instances;
    }

    private LocalDateTime adjustEndDate(LocalDateTime eventEndDate, LocalDate rangeEnd) {
        if (eventEndDate != null && eventEndDate.isBefore(rangeEnd.atTime(23, 59, 59))) {
            return eventEndDate;
        }
        return rangeEnd.atTime(23, 59, 59);
    }

    private boolean isWithinRange(LocalDate date, LocalDate start, LocalDate end) {
        return !date.isBefore(start) && !date.isAfter(end);
    }

    private Page<EventInstance> getPaginatedResult(List<EventInstance> events, Pageable pageable) {
        int start = Math.min((int) pageable.getOffset(), events.size());
        int end = Math.min(start + pageable.getPageSize(), events.size());
        List<EventInstance> content = events.subList(start, end);

        return new PageImpl<>(content, pageable, events.size());
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
