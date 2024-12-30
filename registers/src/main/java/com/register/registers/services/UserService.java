package com.register.registers.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.register.exceptions.AuthenticationException;
import com.register.registers.dto.AuthResponse;
import com.register.registers.entities.Users;
import com.register.registers.repositories.UserRepository;
import com.register.registers.services.jwtServices.JWTService;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    public AuthResponse singIn(Users user){
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        Users userReturn = userRepository.save(user);
        AuthResponse authResponse = new AuthResponse("",userReturn);
        return authResponse;
    }
    public AuthResponse login(Users user) {
        Optional<Users> userFound = userRepository.findByEmail(user.getEmail());
        if (userFound.isEmpty()) {
            System.out.println("Usuario no encontrado");
            throw new AuthenticationException("Credenciales incorrectas");
        }
        System.out.println("Contraseña: " + user.getPassword_hash());
        if (!passwordEncoder.matches(user.getPassword_hash(), userFound.get().getPassword_hash())) {
            System.out.println("Contraseña incorrecta");
            throw new AuthenticationException("Credenciales incorrectas");
        }
        AuthResponse authResponse = new AuthResponse(jwtService.generateToken(user.getEmail()),userFound.get());
        return authResponse;
    }
    
}
