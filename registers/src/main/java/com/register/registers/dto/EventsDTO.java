package com.register.registers.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsDTO {
    private Long eventId; 
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean phoneNotifications;
    private Boolean webNotificacions;
    private int minutesAdvice;

}
