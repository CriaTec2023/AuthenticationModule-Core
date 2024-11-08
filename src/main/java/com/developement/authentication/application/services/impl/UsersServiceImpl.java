package com.developement.authentication.application.services.impl;

import com.developement.authentication.application.dtos.CreationUserDto;
import com.developement.authentication.application.dtos.ResponseSuccessLoginDto;
import com.developement.authentication.application.dtos.UserLoginDto;
import com.developement.authentication.application.dtos.UserUpdateDto;
import com.developement.authentication.application.services.IUserService;
import com.developement.authentication.infrastructure.persistence.UserPersistence;
import com.developement.authentication.presentation.exception.IncorrectPasswordException;
import com.developement.authentication.presentation.exception.InvalidParamException;
import com.developement.authentication.presentation.exception.ResourceNotFoundException;
import com.developement.authentication.domain.entity.UserModel;
import com.developement.authentication.presentation.mapper.FromDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class UsersServiceImpl implements IUserService {


    private final UserPersistence userPersistence;
    private final TokenServiceImpl tokenServiceImpl;


    @Override
    public UserModel creatNewUser(final CreationUserDto user) {

        if ( !userPersistence.existsByEmail(user.email())) {
            String token = tokenServiceImpl.generateToken(user);
            UserModel userModel = FromDto.creationUsertoEntity(user, token);
            return userPersistence.save(userModel);
        }else {
            throw new InvalidParamException("Email já cadastrado");
        }

    }
    @Override
    public ResponseSuccessLoginDto login( final UserLoginDto dto){

        UserDetails findedUser = userPersistence.findByLogin(dto.login());

        if(findedUser == null) throw new ResourceNotFoundException("Senha ou email incorretos");

        UserModel user = userPersistence.findUserModelByLogin(findedUser.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (
             !Objects.equals(user.getPassword(), dto.password())
        ) throw new IncorrectPasswordException("Senha ou email incorretos");

        return ResponseSuccessLoginDto.builder()
                .token(user.getToken())
                .acessInfo(user.getAcess())
                .build();

    }

    @Override
    public UserModel findUserByLogin(final String login){
        return userPersistence.findUserModelByLogin(login).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado")
        );
    }


    public List<UserModel> findAll(){
        return userPersistence.findAll();
    }

    public UserModel setLastAcess(UserModel user) {


        Locale brazil = new Locale("pt", "BR");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").withLocale(brazil)));


        user.setLastAcess(now);

        return userPersistence.save(user);

    }

    public void deleteUser(String id) {
        userPersistence.deleteById(id);
    }

    @Override
    public UserModel updateUser(String id, UserUpdateDto userDto) {
        UserModel userToUpdate = userPersistence.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (userDto.name() != null) {
            log.info("Atualizando nome do usuário {}", userToUpdate.getLogin());
            userToUpdate.setName(userDto.name());
        }

        if (userDto.userType() != null) {
            log.info("Atualizando tipo do usuário {}, de {} para {}", userToUpdate.getLogin(), userToUpdate.getRole(), userDto.userType());
            userToUpdate.setRole(userDto.userType());
        }

        if (userDto.password() != null) {
            log.info("Atualizando senha do usuário {}", userToUpdate.getLogin());
            String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());
            userToUpdate.setPassword(encryptedPassword);
        }

        userToUpdate.setUpdateDate(LocalDateTime.now());

        return userPersistence.save(userToUpdate);
    }



}
