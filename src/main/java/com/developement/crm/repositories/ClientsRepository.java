package com.developement.crm.repositories;

import com.developement.crm.model.Clients;
import com.developement.crm.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {

    Optional<List<Clients>> findClientsByClientOwner_Email (String clientOwner);

    Optional<List<Clients>> findClientsByClientOwner_Login(String Login);

    Optional<Clients> findClientsByEmail(String email);

    List<Clients> findClientsByClientOwner(UserModel user);
}
