package com.carvajal.client.gatewey.out;

import com.carvajal.client.Client;
import reactor.core.publisher.Mono;

public interface ClientRepository {
    Mono<Client> save(Client client);
    Mono<Client> findById(Long id);
    Mono<Client> findByEmail(String email);
}
