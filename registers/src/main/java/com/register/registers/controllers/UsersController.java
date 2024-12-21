package com.register.registers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.register.constants.ErrorMessages;
import com.register.constants.SuccessResponse;
import com.register.registers.entities.Users;
import com.register.registers.interfaces.Response;
import com.register.registers.repositories.UserRepository;

@RestController
@RequestMapping("/spendRegisters")
public class UsersController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping()
    public ResponseEntity<Response<List<Users>>> getUsers(){
        try{
            List<Users> users = userRepository.findAll();
            Response<List<Users>> response = new Response<>(users,SuccessResponse.SUCCESS_GET,false);
            if(users.size() <1){
                response.setMessage(ErrorMessages.NO_DATA_FOUND);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            Response<List<Users>> response = new Response<>(null,ErrorMessages.DEFAULT_ERROR,true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping()
    public ResponseEntity<Response<Users>> postUser(@RequestBody Users user){
        try{
            Users userSaved = userRepository.save(user);
            Response<Users> response = new Response<>(userSaved,SuccessResponse.SUCCESS_POST,false);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }catch(Exception e){
            Response<Users> response = new Response<>(null,ErrorMessages.DEFAULT_ERROR,true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
