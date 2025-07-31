package com.register.registers.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @FutureOrPresent(message = "La fecha inicio no puede estar en el pasado")
    private LocalDateTime startDate;
    @NotNull(message = "La fecha fin es obligatoria")
    @Future(message = "La fecha no puede estar en el pasado")
    private LocalDateTime endDate;
    @NotNull(message = "Tienes que escoger el campo de notificaciones del telefono")
    private Boolean phoneNotifications;
    @NotNull(message = "Tienes que escoger el campo de notificaciones de el sitio web")
    private Boolean webNotifications;
    @NotNull(message = "El campo de minutos del aviso es obligatorio")
    private int minutesAdvice;
    @NotNull(message = "El campo de si es recurrente es obligatorio")
    private Boolean isRecurrent;
    private List<Integer> daysOfWeek;

    @AssertTrue(message = "Si el evento es recurrente, debes especificar los días de la semana")
    public boolean isDaysOfWeekValid() {
        if (Boolean.TRUE.equals(isRecurrent)) {
            return daysOfWeek != null && !daysOfWeek.isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "Los días de la semana deben estar entre 1 (Lunes) y 7 (Domingo)")
    public boolean areDaysOfWeekValid() {
        return daysOfWeek.stream()
                .allMatch(day -> day != null && day >= 1 && day <= 7);
    }

}
