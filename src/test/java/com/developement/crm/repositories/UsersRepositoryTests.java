package com.developement.crm.repositories;

import com.developement.crm.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UsersRepositoryTests {
    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void findUserModelByLoginShouldFind(){
        List<UserModel> users = usersRepository.findAll();

        int size = users.size();
        Assertions.assertEquals(21, size);

    }

    @Test
    public void findUserModelByLoginShoulNotFind(){
        Optional<UserModel> userOpt = usersRepository.findUserModelByLogin("user.falso");

        Assertions.assertFalse(userOpt.isPresent());
    }

}
