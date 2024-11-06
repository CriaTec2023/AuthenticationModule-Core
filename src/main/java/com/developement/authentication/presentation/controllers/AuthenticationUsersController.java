package com.developement.authentication.presentation.controllers;

import com.developement.authentication.application.dtos.CreationUserDto;
import com.developement.authentication.application.dtos.EmailDto;
import com.developement.authentication.application.dtos.EmailObjDto;
import com.developement.authentication.application.dtos.EmailObjPasswordAndTokenDto;
import com.developement.authentication.application.dtos.MessageDto;
import com.developement.authentication.application.dtos.ResponseDto;
import com.developement.authentication.application.dtos.ResponseLoginDto;
import com.developement.authentication.application.dtos.ResponseSuccessLoginDto;
import com.developement.authentication.application.dtos.TokenValidation;
import com.developement.authentication.application.dtos.UserLoginDto;
import com.developement.authentication.application.services.impl.EmailServiceImpl;
import com.developement.authentication.domain.entity.ResetObject;
import com.developement.authentication.domain.entity.UserModel;
import com.developement.authentication.application.services.impl.TokenServiceImpl;
import com.developement.authentication.application.services.impl.UsersServiceImpl;
import com.developement.authentication.infrastructure.persistence.UserPersistence;
import com.developement.authentication.presentation.exception.IncorrectPasswordException;
import com.developement.authentication.presentation.exception.InvalidParamException;
import com.developement.authentication.presentation.exception.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/v2/auth")
public class AuthenticationUsersController {

    private final UsersServiceImpl usersService;
    private final TokenServiceImpl tokenServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final UserPersistence repository;
    private final EmailServiceImpl emailService;



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
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully", content =
                { @Content(mediaType = "application/json", schema =
                @Schema(implementation = ResponseSuccessLoginDto.class)
                ) }),
        @ApiResponse(responseCode = "401", description = "User or password not found", content =
                { @Content(mediaType = "application/json", schema =
                @Schema(implementation = ResponseLoginDto.class)) }) })

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseLoginDto(token, message));
        }
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = MessageDto.class)
                    ) }),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = UserAlreadyExistsException.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro ao processar a solicitação", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = MessageDto.class)) })})
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email enviado com sucesso", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = MessageDto.class)
                    ) }),
            @ApiResponse(responseCode = "409", description = "Erro ao enviar o email", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = InvalidParamException.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro ao processar a solicitação", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = MessageDto.class)) })})
    @PostMapping("/user/reset-code")
    public ResponseEntity<?> resetPassword(@RequestBody EmailObjDto dto){
        try {
            ResetObject resetCode = tokenServiceImpl.generateResetObject(dto.email());

            ResponseDto emailResponse = emailService.sendEmail(EmailDto.builder()
                    .emailTo(dto.email())
                    .subject("Reset Password")
                    .token(resetCode.getResetToken())
                    .build());
            return ResponseEntity.ok(new MessageDto("Success", emailResponse.message()));
        } catch (InvalidParamException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto("Error", e.getMessage()));
        }
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = MessageDto.class)
                    ) }),
            @ApiResponse(responseCode = "409", description = "Erro ao savar sua nova senha", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = InvalidParamException.class))
                    })})
    @PostMapping("/user/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody EmailObjPasswordAndTokenDto dto){
        try {
            tokenServiceImpl.resetPassword(dto.token(), dto.newPassword());
            return ResponseEntity.ok(new MessageDto("Success", "Senha alterada com sucesso"));
        } catch (InvalidParamException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto("Error", e.getMessage()));
        }
    }

}