package com.developement.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nome;
    private String email;
    private String telefone;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel clientOwner;
}
