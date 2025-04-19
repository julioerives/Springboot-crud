package com.register.registers.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.register.registers.entities.Users;
import com.register.registers.services.jwtServices.JWTService;
import com.register.registers.services.utils.CookiesService;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class UserTokenService {
    @Autowired
    CookiesService cookiesService;
    @Autowired
    UserService userService;
    @Autowired
    JWTService jwtService;

    public Users getCurrentUser(HttpServletRequest request) {
        String token = cookiesService.getCookie(request, "token");
        Long userId = jwtService.extractUserId(token);
        return userService.findUserById(userId);
    }

    public Long getCurrentUserId(HttpServletRequest request) {
        String token = cookiesService.getCookie(request, "token");
        Long userId = jwtService.extractUserId(token);
        userService.findUserById(userId);
        return userId;
    }
}
