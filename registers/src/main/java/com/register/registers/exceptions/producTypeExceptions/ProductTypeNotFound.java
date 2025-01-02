package com.register.registers.exceptions.producTypeExceptions;

public class ProductTypeNotFound extends RuntimeException{
    public ProductTypeNotFound(String message){
        super(message);
    }
}
