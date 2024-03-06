package com.developement.authentication.application.dtos;

import com.developement.authentication.domain.entity.Acess;


public record CreationUserDto(
    String name,
    String password,
    String email,
    Acess acessInfo,
    String role

) {
}
