package com.carvajal.auth.service;

import com.carvajal.client.gatewey.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserDetailsReactiveServiceImpl implements ReactiveUserDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return clientRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(email)))
                .map(user -> User.withUsername(email)
                        .password(user.getPassword().getValue())
                        .roles(user.getRole().getValue())
                        .build());
    }
}
