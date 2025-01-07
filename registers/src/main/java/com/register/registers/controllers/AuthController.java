package com.register.registers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.ErrorMessages;
import com.register.registers.constants.SuccessResponse;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.authExceptions.AuthenticationException;
import com.register.registers.exceptions.authExceptions.EmailUsedException;
import com.register.registers.interfaces.Response;
import com.register.registers.services.ResponseService;
// import com.register.registers.repositories.UserRepository;
import com.register.registers.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResponseService responseService;
    @PostMapping("/")
    public ResponseEntity<Response<Users>> logIn(@RequestBody Users user,HttpServletResponse hServletResponse){
        try {
            Users userFound = userService.login(user,hServletResponse); 
            return responseService.buildSuccessResponse(userFound, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return responseService.buildErrorResponse(ErrorMessages.NO_DATA_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<Response<Users>> signIn(@RequestBody Users user,HttpServletResponse hServletResponse){
        try{
            Users userSaved = userService.singIn(user,hServletResponse);
            return responseService.buildSuccessResponse(userSaved, SuccessResponse.SUCCESS_POST, HttpStatus.OK);
        }catch(EmailUsedException e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.EMAIL_USED, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
