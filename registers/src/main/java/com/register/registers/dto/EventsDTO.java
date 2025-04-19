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
    @NotNull(message = "La fecha inicio es obligatoria")
    @FutureOrPresent( message = "La fecha inicio no puede estar en el pasado")
    private LocalDate startDate;
    @NotNull(message = "La fecha fin es obligatoria")
    @Future( message = "La fecha no puede estar en el pasado")
    private LocalDate endDate;
    @NotNull(message = "Tienes que escoger el campo de notificaciones del telefono")
    private Boolean phoneNotifications;
    @NotNull(message = "Tienes que escoger el campo de notificaciones de el sitio web")
    private Boolean webNotificacions;
    @NotNull(message = "El campo de minutos del aviso es obligatorio")
    private int minutesAdvice;

}
