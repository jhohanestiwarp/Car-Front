package com.carvajal.client;

import com.carvajal.client.gatewey.in.ClientUseCase;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.properties.Password;
import com.carvajal.client.properties.Role;
import com.carvajal.commons.exception.EmailAlreadyExistsException;
import com.carvajal.commons.properties.CreatedAt;
import com.carvajal.commons.properties.State;
import com.carvajal.commons.properties.UpdatedAt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ClientUseCaseImp implements ClientUseCase {
    private final ClientRepository clientRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Mono<Client> createClient(Client client) {
        String encodePassword = encryptPassword(client.getPassword().getValue());
        client.setPassword(new Password(encodePassword));
        client.setState(new State("Active"));
        client.setRole(new Role("CLIENT"));
        client.setCreatedAt(new CreatedAt(LocalDateTime.now()));
        client.setUpdatedAt(new UpdatedAt(LocalDateTime.now()));
        return clientRepository.findByEmail(client.getEmail().getValue())
                .flatMap(existing -> {
                    return Mono.<Client>error(new EmailAlreadyExistsException("El correo ya está registrado"));
                })
                .switchIfEmpty(clientRepository.save(client));
    }

    @Override
    public Mono<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
