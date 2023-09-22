package com.developement.crm.repositories;

import com.developement.crm.enums.Roles;
import com.developement.crm.enums.Unidades;
import com.developement.crm.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UsersRepositoryTests {
    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp(){

        UserModel user1 = UserModel.builder()
                .login("lupesms97@gmail.com")
                .password("LuisFelipe97")
                .email("lupesms97@gmail.com")
                .name("Felipe Mota")
                .token("Token")
                .unidade(Unidades.AR)
                .creationDate(LocalDateTime.now())
                .role(Roles.ADMIN)
                .build();

        UserModel user2 = UserModel.builder()
                .login("user2")
                .password("password2")
                .email("user2@example.com")
                .name("User Two")
                .token("token2")
                .unidade(Unidades.BM)
                .creationDate(LocalDateTime.now())
                .role(Roles.USER)
                .build();

        UserModel user3 = UserModel.builder()
                .login("user3")
                .password("password3")
                .email("user3@example.com")
                .name("User Three")
                .token("token3")
                .unidade(Unidades.Resende)
                .creationDate(LocalDateTime.now())
                .role(Roles.USER)
                .build();

            usersRepository.save(user1);
            usersRepository.save(user2);
            usersRepository.save(user3);
    }


//    FIND USER MODEL BY LOGIN TESTS
    @Test
    public void findUserModelByLoginShouldFindSomeThing(){
        Optional<UserModel> userOpt = usersRepository.findUserModelByLogin("lupesms97@gmail.com");
        Assertions.assertTrue(userOpt.isPresent());
    }

    @Test
    public void findUserModelByLoginShouldMatchEmail() {
        Optional<UserModel> userOpt = usersRepository.findUserModelByLogin("lupesms97@gmail.com");
        UserModel user = userOpt.get();

        Assertions.assertEquals("lupesms97@gmail.com", user.getEmail());

    }
    @Test
    public void findUserModelByLoginShoulNotFind(){
        Optional<UserModel> userOpt = usersRepository.findUserModelByLogin("user.falso");
        Assertions.assertFalse(userOpt.isPresent());
    }

//   FIND USER MODEL MY EMAIL TESTS

    @Test
    public void findUserModelByEmailShouldFindSomeThing() {
        Optional<UserModel> userOpt = usersRepository.findUserModelByEmail("user2@example.com");

        Assertions.assertTrue(userOpt.isPresent());
    }

    @Test
    public void findUserModelByEmailShouldMatch(){
        Optional<UserModel> userOpt = usersRepository.findUserModelByEmail("user2@example.com");
        UserModel user = userOpt.get();

        Assertions.assertEquals("user2@example.com", user.getEmail())  ;
    }

    @Test
    public void findUserModelByEmailShoulNotFind(){
        Optional<UserModel> userOpt = usersRepository.findUserModelByEmail("user.falso");

        Assertions.assertFalse(userOpt.isPresent());
    }

//    FIND USER DETAILS BY LOGIN

    @Test
    public void findUserDetailsByLoginShouldFindSomeThing() {
        UserDetails userDtls = usersRepository.findByLogin("user3");

        Assertions.assertFalse(userDtls.getUsername().isEmpty());
    }

    @Test
    public void findUserDetailsByLoginShouldMatch(){
        UserDetails userDtls = usersRepository.findByLogin("user3");

        Assertions.assertEquals("user3", userDtls.getUsername().toString())  ;
    }


    @Test
    public void findUserDetailsByLoginShoulMatchAutorithesAdmin(){
        UserDetails userDtls = usersRepository.findByLogin("lupesms97@gmail.com");
        List<String> userRoles = List.of("ROLE_ADMIN", "ROLE_USER");

        List<String> userRolesDtls = userDtls.getAuthorities()
                .stream()
                .map(Object::toString)
                .toList();

        Assertions.assertEquals(userRoles.get(0),userRolesDtls.get(0));
        Assertions.assertEquals(userRoles.get(1),userRolesDtls.get(1));


    }
    @Test
    public void findUserDetailsByLoginShoulMatchAutorithesUser(){
        UserDetails userDtls = usersRepository.findByLogin("user3");

        List<String> userRoles = List.of("ROLE_USER");

        List<String> userRolesDtls = userDtls.getAuthorities()
                .stream()
                .map(Object::toString)
                .toList();

        Assertions.assertEquals(userRoles.get(0),userRolesDtls.get(0));

    }
}
