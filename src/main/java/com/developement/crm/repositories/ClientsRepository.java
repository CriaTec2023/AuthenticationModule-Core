package com.developement.crm.repositories;

import com.developement.crm.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clients, String> {
}
