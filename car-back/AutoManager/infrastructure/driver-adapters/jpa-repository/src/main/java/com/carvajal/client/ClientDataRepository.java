package com.carvajal.client;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientDataRepository extends ReactiveCrudRepository<ClientData, Long> {
    @Query("SELECT * FROM users WHERE email = :?")
    Mono<ClientData> findByEmail(String email);
}
