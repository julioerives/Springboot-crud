package com.register.registers.services.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.register.registers.interfaces.Response;
@Service
public class ResponseService {
    
    public  <T> ResponseEntity<Response<T>> buildSuccessResponse(T data, String message, HttpStatus status) {
        Response<T> response = new Response<>(data, message, false);
        return new ResponseEntity<>(response, status);
    }

    public <T> ResponseEntity<Response<T>> buildErrorResponse(String errorMessage, HttpStatus status) {
        Response<T> response = new Response<>(null, errorMessage, true);
        return new ResponseEntity<>(response, status);
    }
}
