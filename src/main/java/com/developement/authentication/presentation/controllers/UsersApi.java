package com.developement.authentication.presentation.controllers;

import com.developement.authentication.application.dtos.MessageDto;
import com.developement.authentication.application.dtos.UserUpdateDto;
import com.developement.authentication.application.services.IUserService;
import com.developement.authentication.application.services.impl.UsersServiceImpl;
import com.developement.authentication.domain.entity.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UsersApi {

    private final IUserService usersService;

    @GetMapping("/validation/${email}")
    public ResponseEntity<?> validationUserExists(@RequestParam("email") String email) {
        try {
            UserModel user = usersService.findUserByLogin(email);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }

            return ResponseEntity.status(HttpStatus.FOUND).body("Usuário encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto("Erro", e.getMessage()));
        }
    }

    @DeleteMapping("/user/delete?{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id ) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Sucesso", "Usuário deletado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto("Erro", e.getMessage()));
        }
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateDto user) {
        try {
            usersService.updateUser(id, user);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Sucesso", "Usuário atualizado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto("Erro", e.getMessage()));
        }
    }


}
