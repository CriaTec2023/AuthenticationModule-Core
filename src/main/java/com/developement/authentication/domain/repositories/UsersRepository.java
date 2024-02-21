package com.developement.authentication.domain.repositories;


import org.springframework.security.core.userdetails.UserDetails;



public interface UsersRepository {
    UserDetails findByLogin(String login);


}
