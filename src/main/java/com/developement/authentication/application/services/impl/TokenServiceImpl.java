package com.developement.authentication.application.services.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.developement.authentication.application.dtos.CreationUserDto;
import com.developement.authentication.application.services.ITokenService;
import com.developement.authentication.presentation.exception.InvalidParamException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;



@Service
public class TokenServiceImpl implements ITokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    public String generateToken(CreationUserDto user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.email())
                    .withClaim("roles",user.role())
                    .withClaim("name",user.name())
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

}