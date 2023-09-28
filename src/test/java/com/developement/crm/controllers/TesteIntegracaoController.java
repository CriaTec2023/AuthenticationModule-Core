package com.developement.crm.controllers;

import com.developement.crm.dtos.UserLoginDto;
import com.developement.crm.enums.Roles;
import com.developement.crm.enums.Unidades;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.UsersRepository;
import com.developement.crm.services.TokenService;
import com.developement.crm.services.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TesteIntegracaoController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private TokenService tokenService;

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
                .unidade(Unidades.AR)
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
