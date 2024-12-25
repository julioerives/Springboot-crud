package com.register.registers.dto;

import com.register.registers.entities.Users;

public class AuthResponse {
    private String token;
    private Users user;
    public AuthResponse(String token, Users user) {
        this.token = token;
        this.user = user;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }
    
}
