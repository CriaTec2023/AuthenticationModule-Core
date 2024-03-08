package com.developement.authentication.repositories;

import com.developement.authentication.application.enums.Roles;
import com.developement.authentication.application.enums.Unidades;
import com.developement.authentication.domain.entity.UserModel;
import com.developement.authentication.domain.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



public class UsersRepositoryTests {

}
