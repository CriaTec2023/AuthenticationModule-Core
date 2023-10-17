package com.developement.crm.dtos;

import com.developement.crm.enums.NegotiatonStatus;
import com.developement.crm.model.Clients;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetClientsDto {
    private Long id;

    private String name;
    private String email;
    private String phone;

    private String clientOwner;
    private NegotiatonStatus status;

    public static GetClientsDto toGetClientDto(Clients clients){
        return GetClientsDto.builder()
                .id(clients.getId())
                .name(clients.getName())
                .email(clients.getEmail())
                .phone(clients.getPhone())
                .clientOwner(clients.getClientOwner().getName())
                .status(clients.getStatus())
                .build();
    }
}
