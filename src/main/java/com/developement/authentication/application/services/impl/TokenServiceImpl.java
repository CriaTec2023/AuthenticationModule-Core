package com.developement.authentication.application.services.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.developement.authentication.application.dtos.CreationUserDto;
import com.developement.authentication.application.services.ITokenService;
import com.developement.authentication.domain.entity.ResetObject;
import com.developement.authentication.domain.entity.UserModel;
import com.developement.authentication.infrastructure.persistence.UserPersistence;
import com.developement.authentication.presentation.exception.InvalidParamException;
import com.developement.authentication.presentation.exception.OperationNotCompleteException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

@Transactional
@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements ITokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    private final UserPersistence userPersistence;

    @Override
    public String generateToken(CreationUserDto user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.email())
                    .withClaim("roles",user.role())
                    .withClaim("name",user.name())
                    .withClaim("email",user.email())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }
    @Override
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw  new InvalidParamException(exception.getMessage());
        }
    }

    @Override
    public Instant genExpirationDate() {
        // Adiciona 2 horas ao horário atual
        LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(24);
        // Converte o LocalDateTime para Instant

        Instant time = expirationDateTime.toInstant(ZoneOffset.UTC);


        System.out.println(time);

        return time ;
    }

    @Override
    public ResetObject generateResetObject(String email) {
        String cleanEmail = email.trim();

        try{
            UserModel user = userPersistence.findUserModelByEmail(cleanEmail).orElseThrow(
                    () -> new InvalidParamException("Usuário não encontrado")
            );


            ResetObject resetObject = ResetObject.builder()
                    .resetToken(generateToken())
                    .resetTokenExpireDate(generationTimeForExpire())
                    .resetTokenValid(true)
                    .build();

            user.setResetObject(resetObject);
            userPersistence.save(user);



            return resetObject;

        }
        catch (OperationNotCompleteException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validateResetToken(String token, UserModel user) {

        return Objects.equals(user.getResetObject().getResetToken(), token) &&
                user.getResetObject().isResetTokenValid() &&
                user.getResetObject().getResetTokenExpireDate().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {

        UserModel user = userPersistence.findUserModelByResetObjectResetToken(token).orElseThrow(
                () -> new InvalidParamException("Token inválido")
        );
        if(validateResetToken(token,user)){
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            user.getResetObject().setResetTokenValid(false);
            user.getResetObject().setResetToken("");
            userPersistence.save(user);
            return true;
        }
        return false;

    }
    @Override
    public String generateToken(){
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }

    private LocalDateTime generationTimeForExpire(){

        Locale brazil = new Locale("pt", "BR");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").withLocale(brazil)));

        return now.plusMinutes(30);
    }

}