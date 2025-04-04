package com.carvajal.client;

import com.carvajal.client.services.ClientService;
import com.carvajal.commons.exception.EmailAlreadyExistsException;
import com.carvajal.http.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientMapper mapper;
    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @PostMapping()
    public Mono<ResponseEntity<?>> createClient(@RequestBody Client client) {
        logger.info("Client: creating new client");

        return clientService.createClient(client)
                .flatMap(result -> {
                    if (result == null) {
                        return Mono.just(ResponseHandler.success("Can't register client"));
                    } else {
                        return mapper.toEntityData(result)
                                .map(entityData -> ResponseEntity.status(HttpStatus.CREATED).body(ResponseHandler.success("Success", entityData)));
                    }
                })
                .onErrorResume(EmailAlreadyExistsException.class, e -> {
                    logger.info("Email duplicado: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error(e.getMessage(), HttpStatus.CONFLICT));
                })
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error(e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.info(e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }

    @GetMapping()
    public Mono<ResponseEntity<?>> getClientByEmail() {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context -> {
                    String email = context.getAuthentication().getName();
                    logger.info("Getting client by email: {}", email);
                    return clientService.getClientByEmail(email)
                            .flatMap(result -> {
                                if (result == null) {
                                    return Mono.just(ResponseHandler.success("Client not found"));
                                } else {
                                    return mapper.toEntityData(result)
                                            .map(data -> ResponseEntity.ok(ResponseHandler.success("Success", data)));
                                }
                            });
                })
                .onErrorResume(Exception.class, e -> {
                    logger.error("Error:", e);
                    return Mono.just(ResponseHandler.error("Internal server error"));
                });
    }

}
