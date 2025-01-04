package com.register.registers.exceptions.authExceptions;

public class EmailUsedException extends RuntimeException {
    public EmailUsedException(String message){
        super(message);
    }
}
