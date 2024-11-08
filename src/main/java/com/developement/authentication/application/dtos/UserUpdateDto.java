package com.developement.authentication.application.dtos;

import com.developement.authentication.application.enums.Roles;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record UserUpdateDto(String id,
                            String name,
                            String polo,
                            String phone,
                            String setor,
                            String password,
                            Roles userType){}
