package com.carvajal.client;

import com.carvajal.client.properties.Email;
import com.carvajal.client.properties.FullName;
import com.carvajal.client.properties.Password;
import com.carvajal.client.properties.Role;
import com.carvajal.commons.properties.CreatedAt;
import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.commons.properties.UpdatedAt;
import reactor.core.publisher.Mono;

public class ClientMapper {
    public final Mono<ClientData> toEntityData(Client client) {
        return Mono.just(ClientData.builder()
                .id(client.getId().getValue())
                .fullName(client.getFullName().getValue())
                .email(client.getEmail().getValue())
                .password(client.getPassword().getValue())
                .role(client.getRole().getValue())
                .state(client.getState().getValue())
                .createdAt(client.getCreatedAt().getValue())
                .updatedAt(client.getUpdatedAt().getValue())
                .build());
    }

    public final Mono<ClientData> toNewEntityData(Client client) {
        return Mono.just(ClientData.builder()
                .fullName(client.getFullName().getValue())
                .email(client.getEmail().getValue())
                .password(client.getPassword().getValue())
                .role(client.getRole().getValue())
                .state(client.getState().getValue())
                .createdAt(client.getCreatedAt().getValue())
                .updatedAt(client.getUpdatedAt().getValue())
                .build());
    }

    public final Mono<ClientData> toUpdateEntityData(Client client) {
        return Mono.just(ClientData.builder()
                .id(client.getId().getValue())
                .password(client.getPassword().getValue())
                .build());
    }

    public final Client toDomainModel(ClientData clientData) {
        return new Client(
                new Id(clientData.getId()),
                new FullName(clientData.getFullName()),
                new Email(clientData.getEmail()),
                new Password(clientData.getPassword()),
                new Role(clientData.getRole()),
                new State(clientData.getState()),
                new CreatedAt(clientData.getCreatedAt()),
                new UpdatedAt(clientData.getUpdatedAt())
        );
    }
}