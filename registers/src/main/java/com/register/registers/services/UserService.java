package com.register.registers.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.register.exceptions.AuthenticationException;
import com.register.registers.entities.Users;
import com.register.registers.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    public Users singIn(Users user){
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        return userRepository.save(user);
    }
    public Users login(Users user){
    Optional<Users> userFound = userRepository.findByEmail(user.getEmail());
    if (userFound.isEmpty() || !passwordEncoder.matches(user.getPassword_hash(), userFound.get().getPassword_hash())) {
        throw new AuthenticationException("Credenciales incorrectas");
    }
    return userFound.get();
    }
}
