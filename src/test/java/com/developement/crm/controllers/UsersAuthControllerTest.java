package com.developement.crm.controllers;

import com.developement.crm.dtos.UserLoginDto;
import com.developement.crm.enums.Roles;
import com.developement.crm.enums.Unidades;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.UsersRepository;
import com.developement.crm.services.TokenService;
import com.developement.crm.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;



import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationUsersController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UsersAuthControllerTest {


    @MockBean
    UsersService usersService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
     UsersRepository usersRepository;

    @MockBean
     TokenService tokenService;

    @MockBean
     AuthenticationManager authenticationManager;

    private UserModel user1;

    private UserLoginDto user1LoginDto;

    private UserLoginDto user2LoginDto ;



    @BeforeEach
    void setUp() {
        user1 = UserModel.builder()
                .login("lupesms97@gmail.com")
                .password("LuisFelipe97")
                .email("lupesms97@gmail.com")
                .name("Felipe Mota")
                .token("Token")
                .unidade(Unidades.AR)
                .creationDate(LocalDateTime.now())
                .role(Roles.ADMIN)
                .build();



        user1LoginDto = UserLoginDto.builder()
                .login(user1.getLogin())
                .password(user1.getPassword())
                .build();

        user2LoginDto = UserLoginDto.builder()
                .login("")
                .password("")
                .build();


        // Simula a autenticação com sucesso
        Authentication authentication = new UsernamePasswordAuthenticationToken(user1, null);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Simula a geração de token
        when(tokenService.generateToken(any())).thenReturn(user1.getToken());
    }

    @Test
     void loginUsersShouldReturnTokenAndMessage() throws Exception {


        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1LoginDto)));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(user1.getToken()))
                .andExpect(jsonPath("$.message").value("Usuário logado com sucesso"));

    }


    @Test
    void loginUsersShouldThrowBadCredentialsError() throws Exception {

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Dados de usuário inválidos"));

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2LoginDto)));

        response
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.token").isEmpty())
                .andExpect(jsonPath("$.message").value("Dados de usuário inválidos"));
    }






}
