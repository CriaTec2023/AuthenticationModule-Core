package com.developement.authentication.application.services;

import com.developement.authentication.application.dtos.CreationUserDto;

import java.time.Instant;

public interface ITokenService {


    String generateToken(CreationUserDto user);

    String validateToken(String token);

     Instant genExpirationDate();
}
