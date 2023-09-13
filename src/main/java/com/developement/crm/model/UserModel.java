package com.developement.crm.model;

import com.developement.crm.enums.Unidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name= "users")
@Table(name="users")
@EqualsAndHashCode(of="id")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String login;
    private String password;
    private String email;
    private String name;
    private String token;
    private Unidades unidade;
    private LocalDateTime creationDate;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime acesso;

    public UserModel(String login, String password, String name, Unidades unidade) {
        this.creationDate = LocalDateTime.now();
        this.login = login;
        this.password = password;
        this.name = name;
        this.unidade = unidade;
    }


    public void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

}
