package com.developement.authentication.application.dtos;

import com.developement.authentication.domain.entity.Acess;
import lombok.Builder;

@Builder
public record ResponseSuccessLoginDto(String token, Acess acessInfo){}

