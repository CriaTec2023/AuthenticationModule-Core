package com.developement.authentication.application.services;

import com.developement.authentication.application.dtos.CreationUserDto;
import com.developement.authentication.domain.entity.ResetObject;
import com.developement.authentication.domain.entity.UserModel;

import java.time.Instant;

public interface ITokenService {

    String generateToken(CreationUserDto user);

    String validateToken(String token);

     Instant genExpirationDate();

     ResetObject generateResetObject(String email);

     boolean validateResetToken(String token, UserModel user);

    boolean resetPassword(String token, String newPassword);

    String generateToken();
}
