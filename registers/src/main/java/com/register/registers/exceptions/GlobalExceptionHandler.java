package com.register.registers.exceptions;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.register.registers.constants.ErrorMessages;
import com.register.registers.exceptions.UsersExceptions.UserNotFoundException;
import com.register.registers.exceptions.authExceptions.EmailUsedException;
import com.register.registers.exceptions.authExceptions.AuthenticationException;
import com.register.registers.exceptions.defaultExceptions.ResourceNotFoundException;
import com.register.registers.exceptions.producTypeExceptions.ProductTypeNotFound;
import com.register.registers.interfaces.Response;
import com.register.registers.services.utils.ResponseService;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired 
    ResponseService responseService;

    @ExceptionHandler({
        UserNotFoundException.class,
        ProductTypeNotFound.class,
        ResourceNotFoundException.class,
        AuthenticationException.class
    })
    ResponseEntity<Response<Object>> handleNotFoundException(Exception ex, WebRequest request){
        System.out.println(ex.getMessage());
        return responseService.buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EmailUsedException.class)
    ResponseEntity<Response<Object>> handleResourceExistsException(Exception ex, WebRequest request){
        System.out.println(ex.getMessage());
        return responseService.buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String firstErrorMessage = ex.getBindingResult()
        .getAllErrors()
        .get(0)
        .getDefaultMessage();
        return responseService.buildErrorResponse(firstErrorMessage, HttpStatus.CONFLICT);

    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<Response<Object>> handleDefaultException(Exception ex, WebRequest request){
        System.out.println(ex.getMessage());
        return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
