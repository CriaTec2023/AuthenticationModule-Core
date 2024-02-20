package com.developement.authentication.domain.repositories;

import com.developement.authentication.domain.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UsersRepository {
    UserDetails findByLogin(String login);


}
