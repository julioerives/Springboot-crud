package com.register.registers.entities.mongo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



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
    private LocalDateTime startDate;

    private Long userId;
    private LocalDateTime endDate;
    private String eventName;
    private Boolean phoneNotifications;
    private Boolean webNotifications;
    private int minutesAdvice;


}
