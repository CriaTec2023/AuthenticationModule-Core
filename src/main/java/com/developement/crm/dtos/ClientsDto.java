package com.developement.crm.dtos;


import com.developement.crm.enums.NegotiatonStatus;
import com.developement.crm.model.Clients;
import com.developement.crm.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientsDto {

    private String name;
    private String email;
    private String phone;


// MUDAR O NOME DESSE METODO
    public static Clients toClientDto(ClientsDto clientsDto, UserModel owner){
        return Clients.builder()
                .name(clientsDto.getName())
                .email(clientsDto.getEmail())
                .phone(clientsDto.getPhone())
                .status(NegotiatonStatus.NEW)
                .clientOwner(owner)
                .build();
    }
}
