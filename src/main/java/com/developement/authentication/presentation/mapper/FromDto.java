package com.developement.authentication.presentation.mapper;

import com.developement.authentication.application.dtos.CreationUserDto;
import com.developement.authentication.application.enums.Roles;
import com.developement.authentication.domain.entity.Acess;
import com.developement.authentication.domain.entity.ResetObject;
import com.developement.authentication.domain.entity.UserModel;
import lombok.Builder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;



@Builder
public class FromDto {

    public static UserModel creationUsertoEntity(CreationUserDto dto, String token) {

         return UserModel.builder()
                .login(dto.email())
                .password(new BCryptPasswordEncoder().encode(dto.password()))
                .email(dto.email())
                .name(dto.name())
                .token(token)
                .creationDate(LocalDateTime.parse(creatDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")))
                .updateDate(LocalDateTime.parse(creatDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")))
                .role(Roles.valueOf(dto.role()))
                .active(true)
                .acess(Acess.builder()
                        .AvailiableCompanies(dto.acessInfo().getAvailiableCompanies())
                        .permission(dto.acessInfo().getPermission())
                        .build())
                 .resetObject(ResetObject.builder()
                         .resetTokenValid(false)
                         .resetToken("")
                         .resetTokenExpireDate(LocalDateTime.now())
                         .build())
                .build();



    }


    private static String creatDate() {
        Locale brazil = new Locale("pt", "BR");
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").withLocale(brazil));
    }




}
