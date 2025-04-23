package com.register.registers.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.EventsDTO;
import com.register.registers.dto.PageDTOResponse;
import com.register.registers.entities.Events;
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
    @GetMapping("/")
    public ResponseEntity<Response<PageDTOResponse<Events>>> getEvents(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            HttpServletRequest request
        ) {
        Page<Events> PageResponse = this.eventsService.getAllEvents(page, size, request);
        PageDTOResponse<Events> response = new PageDTOResponse<>(PageResponse);
        return responseService.buildSuccessResponse(response, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<Response<Events>> addEvent(@Valid @RequestBody EventsDTO eventsDTO, HttpServletRequest request) {
        Events event = this.eventsService.addEvent(eventsDTO, request);
        return responseService.buildSuccessResponse(event, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);
    }
}
