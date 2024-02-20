package com.developement.authentication.controllers;

import com.developement.authentication.application.dtos.UserLoginDto;
import com.developement.authentication.application.enums.Roles;
import com.developement.authentication.application.enums.Unidades;
import com.developement.authentication.domain.entity.UserModel;
import com.developement.authentication.domain.repositories.UsersRepository;
import com.developement.authentication.application.services.impl.TokenServiceImpl;
import com.developement.authentication.application.services.impl.UsersServiceImpl;
import com.developement.authentication.presentation.controllers.AuthenticationUsersController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TesteIntegracaoController {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AuthenticationUsersController authenticationUsersController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    private UserModel user1;

    private UserLoginDto user1LoginDto;

    @BeforeEach
    void setUp() {
        user1 = UserModel.builder()
                .login("lupesms97@gmail.com")
                .password("LuisFelipe97")
                .email("lupesms97@gmail.com")
                .name("Felipe Mota")
                .token("Token")

                .creationDate(LocalDateTime.now())
                .role(Roles.ADMIN)
                .build();

        user1LoginDto = UserLoginDto.builder()
                .login(user1.getLogin())
                .password(user1.getPassword())
                .build();

    }
    @Test
    public void LoginShouldReturnToken() throws Exception {

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1LoginDto)));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(user1.getToken()))
                .andExpect(jsonPath("$.message").value("Usuário logado com sucesso"));

    }









}
