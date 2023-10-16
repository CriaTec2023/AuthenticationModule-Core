package com.developement.crm.model;

import com.developement.crm.enums.NegotiatonStatus;
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
    private Long id;

    private String name;
    private String email;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel clientOwner;
    @Enumerated(EnumType.STRING)
    private NegotiatonStatus status;

    public Clients(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;

    }
}
