package com.register.registers.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsDTO {
    private Long eventId; 
    @NotNull(message = "El nombre del evento es obligatorio")
    @Size(min = 1, max = 50, message = "El nombre del evento debe tener entre 1 y 50 caracteres")
    private String eventName;
    @NotNull(message = "La fecha inicio es obligatoria")
    @FutureOrPresent( message = "La fecha inicio no puede estar en el pasado")
    private LocalDate startDate;
    @NotNull(message = "La fecha fin es obligatoria")
    @Future( message = "La fecha no puede estar en el pasado")
    private LocalDate endDate;
    @NotNull(message = "Tienes que escoger el campo de notificaciones del telefono")
    private Boolean phoneNotifications;
    @NotNull(message = "Tienes que escoger el campo de notificaciones de el sitio web")
    private Boolean webNotifications;
    @NotNull(message = "El campo de minutos del aviso es obligatorio")
    private int minutesAdvice;

}
