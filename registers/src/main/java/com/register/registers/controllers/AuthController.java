package com.register.registers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.SuccessResponse;
import com.register.registers.entities.Users;
import com.register.registers.interfaces.Response;
import com.register.registers.services.users.UserService;
import com.register.registers.services.utils.ResponseService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("/")
    public ResponseEntity<Response<Users>> logIn(@RequestBody Users user, HttpServletResponse hServletResponse) {
        Users userFound = userService.login(user, hServletResponse);
        return responseService.buildSuccessResponse(userFound, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }

    @PostMapping("/singIn")
    public ResponseEntity<Response<Users>> signIn(@RequestBody Users user, HttpServletResponse hServletResponse) {
        System.out.println(user);
        Users userSaved = userService.singIn(user, hServletResponse);
        return responseService.buildSuccessResponse(userSaved, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);

    }

}
