package com.developement.crm.controllers;

import com.developement.crm.dtos.ClientsDto;
import com.developement.crm.dtos.GetClientsDto;
import com.developement.crm.model.Clients;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.ClientsRepository;
import com.developement.crm.services.AuthenticationService;
import com.developement.crm.services.ClientsService;
import com.developement.crm.services.UsersService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientsController {

    private final ClientsService clientsService;
    private final UsersService usersService;

    @Autowired
    public ClientsController(
            ClientsService clientsService,
            UsersService usersService
                             ) {
        this.clientsService = clientsService;
        this.usersService = usersService;

    }


    @PostMapping("/createClient")
    public ResponseEntity<?> createNewClient(@RequestBody ClientsDto client){
        UserModel owner = usersService.findUserByLogin(AuthenticationService.getUserbySession());

        Clients clientsModel = ClientsDto.toClientDto(client, owner);

        clientsService.creatNewClient(clientsModel);

        GetClientsDto getClientsDto = GetClientsDto.toGetClientDto(clientsModel);

        return new ResponseEntity<>(getClientsDto, HttpStatus.OK);
    }

    @PostMapping("/createClients")
    public ResponseEntity<?> createNewClients(@RequestBody List<ClientsDto> clients){
        List<String> userAdd = new ArrayList<>();
        UserModel owner = usersService.findUserByLogin(AuthenticationService.getUserbySession());

        for (ClientsDto client: clients) {
            Clients clientsModel = ClientsDto.toClientDto(client, owner);
            clientsService.creatNewClient(clientsModel);
            userAdd.add(client.getName());
        }


        return new ResponseEntity<>("Foram adicionados com sucesso"+userAdd, HttpStatus.OK);
    }


    @GetMapping("/getMyClients")
    public ResponseEntity<?> getMyClients(){

        List<GetClientsDto> clientsReturn = new ArrayList<>();

        List<Clients> clients = clientsService.getAllClients();

        for (Clients client: clients) {
            GetClientsDto clientsDto = GetClientsDto.toGetClientDto(client);
            clientsReturn.add(clientsDto);
        }


        if (clients.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientsReturn, HttpStatus.OK);
    }






}
