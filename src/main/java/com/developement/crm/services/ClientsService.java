package com.developement.crm.services;

import com.developement.crm.enums.NegotiatonStatus;
import com.developement.crm.model.Clients;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.ClientsRepository;
import com.developement.crm.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
public class ClientsService {
    private final ClientsRepository clientsRepository;

    private final UsersRepository usersRepository;


    @Autowired
    public ClientsService(ClientsRepository clientsRepository,
                          UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.clientsRepository = clientsRepository;

    }


    public Clients creatNewClient(Clients client) {

        Clients newClient = new Clients(client.getName(), client.getEmail(), client.getPhone());

        newClient.setStatus(NegotiatonStatus.NEW);

        String login = AuthenticationService.getUserbySession();
        usersRepository.findUserModelByLogin(login).ifPresent(newClient::setClientOwner);


//        usersRepository.findUserModelByLogin(login).ifPresent(owner -> {
//            newClient.setClientOwner(owner);
//        });

        clientsRepository.save(newClient);

        return newClient;
    }
//
//    public Clients updateClient(Clients client) {
//        clientsRepository.save(client);
//        return client;
//    }
//
//    public Clients updateClientStatusWithEmail(String email, NegotiatonStatus status) {
//
//        Clients client = clientsRepository.findClientsByEmail(email).get();
//        client.setStatus(status);
//        clientsRepository.save(client);
//        return client;
//    }
//
//    public List<Clients> getClientsByOwner() {
//
//        String login = AuthenticationService.getUserbySession();
//
//
//        return clientsRepository.findClientsByClientOwner_Email(login).get();
//
//    }

    public List<Clients> getAllClients() {


        String loginSession = AuthenticationService.getUserbySession();



        UserModel user = usersRepository.findUserModelByLogin(loginSession).get();


//        List<Clients> listOfClients =  clientsRepository.findClientsByClientOwner(user);

        List<Clients> listOfClients =  clientsRepository.findClientsByClientOwner_Login(user.getLogin()).get();


        if (listOfClients.isEmpty()){
            log.info("Sem clientes para o usuario: "+loginSession );
        }
        return listOfClients;
    }

}
