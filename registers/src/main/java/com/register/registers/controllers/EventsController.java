package com.register.registers.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.EventInstance;
import com.register.registers.dto.EventsDTO;
import com.register.registers.dto.PageDTOResponse;
import com.register.registers.entities.mongo.Events;
import com.register.registers.interfaces.Response;
import com.register.registers.services.events.EventsService;
import com.register.registers.services.utils.ResponseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    ResponseService responseService;
    @Autowired
    EventsService eventsService;

    @GetMapping("")
    public ResponseEntity<Response<PageDTOResponse<EventInstance>>> getEvents(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) {
        Page<EventInstance> pageResponse = this.eventsService.getAllEvents(page, size, startDate, endDate, request);
        PageDTOResponse<EventInstance> response = PageDTOResponse.of(pageResponse);
        return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Response<Events>> addEvent(@Valid @RequestBody EventsDTO eventsDTO,
            HttpServletRequest request) {
        Events event = this.eventsService.addEvent(eventsDTO, request);
        return responseService.buildSuccessResponse(event, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Response<Object>> deleteEvent(@PathVariable("eventId") String eventId,
            HttpServletRequest request) {
        this.eventsService.deleteEvent(eventId, request);
        return responseService.buildSuccessResponse(null, SuccessResponse.SUCCESS_DELETE, HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Response<Events>> updateEvent(@PathVariable("eventId") String eventId,
            @Valid @RequestBody EventsDTO eventsDTO, HttpServletRequest request) {
        Events event = this.eventsService.updateEvent(eventId, eventsDTO, request);
        return responseService.buildSuccessResponse(event, SuccessResponse.SUCCESS_PUT, HttpStatus.OK);
    }

    @GetMapping("/byDates")
    public ResponseEntity<Response<List<Events>>> getEventsByDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            HttpServletRequest request) {
        List<Events> response = this.eventsService.getEventsByDates(startDate, endDate, request);
        return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }

}
