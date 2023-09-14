package com.developement.crm.model;

import com.developement.crm.enums.Roles;
import com.developement.crm.enums.Unidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name= "users")
@Table(name="users")
@EqualsAndHashCode(of="id")
public class UserModel implements UserDetails {

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
    private Roles role;

    public UserModel(String login, String password, String name, Unidades unidade) {
        this.creationDate = LocalDateTime.now();
        this.login = login;
        this.password = password;
        this.name = name;
        this.unidade = unidade;
    }
    public UserModel(String login, String password, Roles role){
        this.login = login;
        this.password = password;
        this.role = role;
    }


    public void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (this.role == Roles.ADMIN)?
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER")):
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
