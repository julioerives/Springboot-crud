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
import com.register.registers.dto.AuthResponse;
import com.register.registers.entities.Users;
import com.register.registers.exceptions.AuthenticationException;
import com.register.registers.interfaces.Response;
import com.register.registers.services.ResponseService;
// import com.register.registers.repositories.UserRepository;
import com.register.registers.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResponseService responseService;
    @PostMapping("/")
    public ResponseEntity<Response<AuthResponse>> logIn(@RequestBody Users user){
        try {
            AuthResponse userFound = userService.login(user); 
            return responseService.buildSuccessResponse(userFound, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return responseService.buildErrorResponse(ErrorMessages.NO_DATA_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return responseService.buildErrorResponse(ErrorMessages.DEFAULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<Response<AuthResponse>> signIn(@RequestBody Users user){
        try{
            
            AuthResponse userSaved = userService.singIn(user);
            Response<AuthResponse> response = new Response<>(userSaved,SuccessResponse.SUCCESS_POST,false);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }catch(Exception e){
            Response<AuthResponse> response = new Response<>(null,ErrorMessages.DEFAULT_ERROR,true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
