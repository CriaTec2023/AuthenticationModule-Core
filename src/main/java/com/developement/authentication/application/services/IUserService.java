package com.developement.authentication.application.services;

import com.developement.authentication.application.dtos.CreationUserDto;
import com.developement.authentication.application.dtos.ResponseSuccessLoginDto;
import com.developement.authentication.application.dtos.UserLoginDto;
import com.developement.authentication.domain.entity.UserModel;

public interface IUserService {

    UserModel creatNewUser(CreationUserDto user);

    ResponseSuccessLoginDto login(UserLoginDto dto);

    UserModel findUserByLogin(String login);


}
