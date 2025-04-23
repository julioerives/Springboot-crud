package com.register.registers.services.utils;


import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookiesService {
    public void addCookie(HttpServletResponse response, String name, String value, int ageDays) {
        System.out.println("AGREGAR COOKIE");
        System.out.println("addCookie: " + name + " = " + value + " ageDays: " + ageDays);
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(ageDays * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    public String getCookie(HttpServletRequest request, String name) {
        System.out.println(
            "OBTENER COOKIE: "
        );
        System.out.println("Nombre cookie"+name);

        Cookie cookie = WebUtils.getCookie(request, name);
        System.out.println("cookie: " + cookie);
        if (cookie != null) {
            return cookie.getValue();
        }
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
