package com.developement.authentication.presentation.controllers;

import com.developement.authentication.application.dtos.*;
import com.developement.authentication.domain.entity.UserModel;
import com.developement.authentication.application.services.impl.TokenServiceImpl;
import com.developement.authentication.application.services.impl.UsersServiceImpl;
import com.developement.authentication.infrastructure.persistence.UserPersistence;
import com.developement.authentication.presentation.exception.IncorrectPasswordException;
import com.developement.authentication.presentation.exception.InvalidParamException;
import com.developement.authentication.presentation.exception.UserAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("v2/auth")
public class AuthenticationUsersController {

    private final UsersServiceImpl usersService;
    private final TokenServiceImpl tokenServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final UserPersistence repository;



//    @GetMapping("/make")
//    public String makeChanges(){
//
//        try{
//
//            List<UserModel> users = usersRepository.findAll();
//
//            for(UserModel user : users){
//                String oldPassword = user.getPassword();
//                String newPassword = new BCryptPasswordEncoder().encode(oldPassword);
//
//                String token = tokenService.generateToken(user);
//
//
//                user.setPassword(newPassword);
//
//                usersRepository.save(user);
//            }
//            return "feito com sucesso";
//
//        } catch (Exception e){
//            throw new RuntimeException(e);
//        }
//
//    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto data){

        try {
            UserModel user = usersService.findUserByLogin(data.login());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }

            var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var authentication = authenticationManager.authenticate(userNamePassword);

            if (!authentication.isAuthenticated()) {
                throw new IncorrectPasswordException("Erro ao autenticar usuário");
            }

            UserModel userWithAAcessSeted = usersService.setLastAcess(user);

            return ResponseEntity.ok(new ResponseSuccessLoginDto(userWithAAcessSeted.getToken(), userWithAAcessSeted.getAcess()));

        } catch (BadCredentialsException e) {
            String message = "Dados de usuário inválidos";
            var token = "";
            return ResponseEntity.badRequest().body(new ResponseLoginDto(token, message));
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody @Valid CreationUserDto user){

        try {
            UserDetails userDetails = repository.findByLogin(user.email());
            if (userDetails != null) {
                throw new UserAlreadyExistsException("Usuário já cadastrado");
            }

            usersService.creatNewUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageDto("Sucess","Usuário cadastrado com sucesso"));

        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto("Erro", e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto("Erro", "Ocorreu um erro ao processar a solicitação"));
        }


    }


    @GetMapping("/validation")
    public TokenValidation validation() {
        try {
            return new TokenValidation("Success", "The Token is Valid");
        } catch (InvalidParamException e) {
            return new TokenValidation("Fail", "The Token is Invalid");
        }
    }


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(usersService.findAll());
    }
}