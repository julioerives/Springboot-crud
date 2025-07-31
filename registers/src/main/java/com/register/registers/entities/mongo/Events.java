package com.register.registers.entities.mongo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {
    @Id
    private String eventId;
    private Boolean isRecurrent;
    private Long userId;
    @Indexed
    private LocalDateTime startDate;
    @Indexed
    private LocalDateTime endDate;
    private String eventName;
    private List<Integer> daysOfWeek;
    private Boolean phoneNotifications;
    private Boolean webNotifications;
    private int minutesAdvice;
    private Set<LocalDateTime> exDates;

    @JsonIgnore
    private Boolean active;



}
