package com.register.registers.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.register.registers.constants.SuccessResponse;
import com.register.registers.dto.LoginDTO;
import com.register.registers.entities.postgres.Users;
import com.register.registers.interfaces.Response;
import com.register.registers.services.users.UserService;
import com.register.registers.services.utils.ResponseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("")
    public ResponseEntity<Response<Users>> logIn(@Valid @RequestBody LoginDTO user, HttpServletResponse hServletResponse) {
        Users userFound = userService.login(user, hServletResponse);
        return responseService.buildSuccessResponse(userFound, SuccessResponse.SUCCESS_GET, HttpStatus.OK);
    }

    @PostMapping("/singIn")
    public ResponseEntity<Response<Users>> signIn(@Valid @RequestBody LoginDTO user, HttpServletResponse hServletResponse) {
        Users userSaved = userService.singIn(user, hServletResponse);
        return responseService.buildSuccessResponse(userSaved, SuccessResponse.SUCCESS_POST, HttpStatus.CREATED);

    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verifyToken(HttpServletRequest request) {
        boolean isValid = this.userService.isUserAuthenticated(request);
        return ResponseEntity.ok(Collections.singletonMap("isValid", isValid));
    }

}
