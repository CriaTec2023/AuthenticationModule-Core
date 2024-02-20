package com.developement.authentication.application.dtos;


import lombok.Builder;

@Builder
public record UserLoginDto (
    String login,
    String password
            ) {}
