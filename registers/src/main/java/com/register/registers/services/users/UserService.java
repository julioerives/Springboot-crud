package com.register.registers.services.users;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.register.registers.entities.Users;
import com.register.registers.exceptions.UsersExceptions.UserNotFoundException;
import com.register.registers.exceptions.authExceptions.AuthenticationException;
import com.register.registers.exceptions.authExceptions.EmailUsedException;
import com.register.registers.repositories.UserRepository;
import com.register.registers.services.jwtServices.JWTService;
import com.register.registers.services.utils.CookiesService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private CookiesService cookiesService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    public Users singIn(Users user,HttpServletResponse hServletResponse){
        Optional<Users> userFound = userRepository.findByEmail(user.getEmail());
        if (userFound.isPresent()) {
            throw new EmailUsedException("Correo en uso");
        }
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        Users userReturn = userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail(), user.getUserId());

        cookiesService.addCookie(hServletResponse, "JWToken", token, 10);
        return userReturn;
    }
    public Users login(Users user,HttpServletResponse hServletResponse) {
        Optional<Users> userFound = userRepository.findByEmail(user.getEmail());
        if (userFound.isEmpty()) {
            throw new AuthenticationException("Credenciales incorrectas");
        }
        if (!passwordEncoder.matches(user.getPassword_hash(), userFound.get().getPassword_hash())) {
            throw new AuthenticationException("Credenciales incorrectas");
        }
        String token = jwtService.generateToken(user.getEmail(), user.getUserId());
        cookiesService.addCookie(hServletResponse, "JWToken", token, 10);
        return userFound.get();
    }
    public Users  findUserById(Long id){
        return userRepository.findById(id)
        .orElseThrow(()->new UserNotFoundException("Usuario no encontrado"));
    }

    public Boolean isUserAuthenticated(HttpServletRequest request){
        boolean isValid = false;
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
            isValid = Arrays.stream(cookies)
                    .filter(c -> "token".equals(c.getName()))
                    .anyMatch(c -> jwtService.validateToken(c.getValue()));
        }
        return isValid;
    } 
    
}
