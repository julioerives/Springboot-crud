package com.register.registers.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message){
        super(message);
    }

}
