package com.register.registers.services.utils;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookiesService {
    public void addCookie(HttpServletResponse response, String name, String value, int ageDays) {
        System.out.println("addCookie: " + name + " = " + value + " ageDays: " + ageDays);
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(ageDays * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setHeader("Set-Cookie", String.format("%s=%s; Path=/; HttpOnly; SameSite=None", name, value));
    }
    public String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        System.out.println(
            getTokenFromRequest(request)
        );
        if (cookies != null) {
            Arrays.stream(cookies).forEach(cookie ->
                System.out.println("Cookie Name: " + cookie.getName() + ", Value: " + cookie.getValue())
            );
    
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(name))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        System.out.println("No cookies found in the request.");
        return null;
    }
    public void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    public String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
