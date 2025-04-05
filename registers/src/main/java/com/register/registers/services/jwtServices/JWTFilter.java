package com.register.registers.services.jwtServices;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.register.registers.constants.ErrorMessages;
import com.register.registers.services.utils.CookiesService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired 
    private JWTService jwtService;
    @Autowired
    private CookiesService cookiesService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = cookiesService.getCookie(request, "token");
        String useremail = null;
    
        try {
            if (token != null && jwtService.validateToken(token)) {
                useremail = jwtService.extractEmail(token);
    
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(useremail, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
    
            } else {
                sendAuthenticationError(response, "invalid_token", ErrorMessages.DEFAULT_ERROR_TOKEN);
                return;
            }
    
        } catch (ExpiredJwtException e) {
            sendAuthenticationError(response, "expired_token", ErrorMessages.EXPIRED_TOKEN);
            return;
    
        } catch (MalformedJwtException e) {
            sendAuthenticationError(response, "invalid_token", ErrorMessages.DEFAULT_ERROR_TOKEN);
            return;
        }
    
        filterChain.doFilter(request, response);
    }
    
    private void sendAuthenticationError(HttpServletResponse response, String error, String description) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", String.format("Bearer error=\"%s\", error_description=\"%s\"", error, description));
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
    }
}
