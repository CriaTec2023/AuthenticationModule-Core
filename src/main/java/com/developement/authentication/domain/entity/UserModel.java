package com.developement.authentication.domain.entity;

import com.developement.authentication.application.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Column(unique = true)
    private String email;
    private String name;
    private String token;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private LocalDateTime lastAcess;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private boolean active = true;
    @Embedded
    private Acess acess ;
    @Embedded
    private ResetObject resetObject;



    public String getRoleString(){
        return this.role.toString();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (this.role == Roles.ADMIN)?
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER")):
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
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
