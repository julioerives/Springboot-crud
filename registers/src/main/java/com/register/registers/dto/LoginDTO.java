package com.register.registers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
    @NotNull(message = "El campo de email es obligatorio")
    @Email(message = "El email no es valido")
    private String email;
    @NotNull(message = "El campo de contraseña es obligatorio")
    @Min(value = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password_hash;
}
