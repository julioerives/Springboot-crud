package com.register.registers.services.jwtServices;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.register.registers.constants.ErrorMessages;

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
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headers = request.getHeader("Authorization");
        String token = null;
        String useremail = null;
        try{
            if (headers != null && headers.startsWith("Bearer ")) {
                token = headers.substring(7);
                useremail = jwtService.extractEmail(token);
            }

            if (jwtService.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(useremail, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                sendAuthenticationError(response, "invalid_token", ErrorMessages.DEFAULT_ERROR_TOKEN);
            }
            
        }catch(ExpiredJwtException e){
            sendAuthenticationError(response, "expired_token", ErrorMessages.EXPIRED_TOKEN);
        }catch(MalformedJwtException e){
            sendAuthenticationError(response, "invalid_token", ErrorMessages.EXPIRED_TOKEN);
        }
        filterChain.doFilter(request, response);
    }
    private void sendAuthenticationError(HttpServletResponse response,String error,String description){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", String.format("Bearer error=\"%s\", error_description=\"%s\"", error, description));
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
    }
}
