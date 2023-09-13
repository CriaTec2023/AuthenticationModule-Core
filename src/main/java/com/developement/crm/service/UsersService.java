package com.developement.crm.service;

import com.developement.crm.exceptionHandlers.UserNotFoundException;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;


import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;


    public UserModel creatNewUser(UserModel user) {
        if(user.getId() != null && userRepository.findUserModelByLogin(user.getLogin()).isPresent()) {
            return user;
        } else {
            user.generateToken();
            userRepository.save(user);
            return user;
        }

    }

    public String login(String login, String password){

        Optional<UserModel> findUser = userRepository.findUserModelByLogin(login);
        if(findUser.isPresent()) {
            UserModel user = findUser.get();
            if(user.getPassword().equals(password)) {
                return user.getToken();
            }else {
                throw new UserNotFoundException("Usuario com senha incorreta");
            }
        }else {
            throw new UserNotFoundException("Email não encontrado com o login: " + login);
        }
    }
}
