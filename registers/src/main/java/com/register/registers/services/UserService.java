package com.register.registers.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.register.registers.dto.AuthResponse;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.UsersExceptions.UserNotFoundException;
import com.register.registers.exceptions.authExceptions.AuthenticationException;
import com.register.registers.exceptions.authExceptions.EmailUsedException;
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
        Optional<Users> userFound = userRepository.findByEmail(user.getEmail());
        if (userFound.isPresent()) {
            throw new EmailUsedException("Correo en uso");
        }
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        Users userReturn = userRepository.save(user);
        AuthResponse authResponse = new AuthResponse(jwtService.generateToken(user.getEmail()),userReturn);
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
    public Users  findUserById(Long id){
        return userRepository.findById(id)
        .orElseThrow(()->new UserNotFoundException("Usuario no encontrado"));
    }
    
}
