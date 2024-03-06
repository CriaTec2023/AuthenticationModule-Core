package com.developement.authentication.infrastructure.persistence;

import com.developement.authentication.domain.entity.UserModel;
import com.developement.authentication.domain.repositories.UsersRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserPersistence extends JpaRepository<UserModel, String>, UsersRepository {

    UserDetails findByLogin(String login);

    Optional<UserModel> findUserModelByEmail(String email);

    Optional<UserModel> findUserModelByLogin(String login);

    boolean existsByEmail(String id);

    Optional<UserModel> findUserModelByResetObjectResetToken(String token);

}
