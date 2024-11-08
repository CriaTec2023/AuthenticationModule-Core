package com.developement.authentication.application.dtos;

import com.developement.authentication.domain.entity.Acess;
import com.fasterxml.jackson.annotation.JsonProperty;


public record CreationUserDto(
    String name,
    String password,
    String email,
    @JsonProperty(required = false) Acess acessInfo,
    String role

) {
}
