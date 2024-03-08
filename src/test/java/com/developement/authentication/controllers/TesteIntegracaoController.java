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


public class TesteIntegracaoController {









}
