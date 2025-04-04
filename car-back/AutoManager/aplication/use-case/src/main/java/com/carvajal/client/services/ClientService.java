package com.carvajal.client.services;

import com.carvajal.client.Client;
import com.carvajal.client.gatewey.in.ClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientUseCase {

    private final ClientUseCase clientUseCase;

    @Override
    public Mono<Client> createClient(Client client) {
        return clientUseCase.createClient(client);
    }

    @Override
    public Mono<Client> getClientByEmail(String email) {
        return clientUseCase.getClientByEmail(email);
    }
}
