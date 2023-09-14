package com.developement.crm.controllers;

import com.developement.crm.dtos.UserLoginDto;
import com.developement.crm.dtos.UsersDto;
import com.developement.crm.exceptionHandlers.UserNotFoundException;
import com.developement.crm.model.UserModel;
import com.developement.crm.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersControllers {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto data){
        try {
            String token = usersService.login(data.getLogin(), data.getPassword());
            if (token == null) {
                return ResponseEntity.badRequest().body("Invalid username or password");
            }

            // Crie um objeto JSON para incluir o token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok().body(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }


    @PostMapping("/newUsers")
    public ResponseEntity<UsersDto> create(@RequestBody @Valid UsersDto user){
        UserModel userModel = UsersDto.convertToUserModel(user);
        usersService.creatNewUser(userModel);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
