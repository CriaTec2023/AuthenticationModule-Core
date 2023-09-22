package com.developement.crm.services;

import com.developement.crm.enums.Roles;
import com.developement.crm.enums.Unidades;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTests {


    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

//
//    @BeforeEach
//    void setUp(){
//        UserModel user1 = UserModel.builder()
//                .login("lupesms97@gmail.com")
//                .password("LuisFelipe97")
//                .email("lupesms97@gmail.com")
//                .name("Felipe Mota")
//                .token("Token")
//                .unidade(Unidades.AR)
//                .creationDate(LocalDateTime.now())
//                .role(Roles.ADMIN)
//                .build();
//
//        UserModel user2 = UserModel.builder()
//                .login("user2")
//                .password("password2")
//                .email("user2@example.com")
//                .name("User Two")
//                .token("token2")
//                .unidade(Unidades.BM)
//                .creationDate(LocalDateTime.now())
//                .role(Roles.USER)
//                .build();
//
//        UserModel user3 = UserModel.builder()
//                .login("user3")
//                .password("password3")
//                .email("user3@example.com")
//                .name("User Three")
//                .token("token3")
//                .unidade(Unidades.Resende)
//                .creationDate(LocalDateTime.now())
//                .role(Roles.USER)
//                .build();
//
//
//
//    }

    @Test
    public void loginUsersShouldReturnToken(){
        UserModel user = UserModel.builder()
                .login("lupesms97@gmail.com")
                .password("LuisFelipe97")
                .email("lupesms97@gmail.com")
                .name("Felipe Mota")
                .token("a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146")
                .unidade(Unidades.AR)
                .creationDate(LocalDateTime.now())
                .role(Roles.ADMIN)
                .build();

        Mockito.when(usersRepository.findUserModelByLogin("lupesms97@gmail.com"))
                .thenReturn(Optional.of(user));

        String token = usersService.login(user.getLogin(), user.getPassword());

        Assertions.assertEquals(token, user.getToken());

    }




}
