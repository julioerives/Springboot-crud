package com.register.registers.dto;

import java.time.LocalDateTime;

import com.register.registers.entities.mongo.Events;

import lombok.Data;

@Data
public class EventInstance {
    private String eventId;
    private String eventName;
    private LocalDateTime startDate;
    private Boolean phoneNotifications;
    private Boolean webNotifications;
    private int minutesAdvice;

    public EventInstance(Events event, LocalDateTime startDate) {
        this.eventId = event.getEventId();
        this.eventName = event.getEventName();
        this.startDate = startDate;
        this.phoneNotifications = event.getPhoneNotifications();
        this.webNotifications = event.getWebNotifications();
        this.minutesAdvice = event.getMinutesAdvice();
    }

    // Getters y setters
}

