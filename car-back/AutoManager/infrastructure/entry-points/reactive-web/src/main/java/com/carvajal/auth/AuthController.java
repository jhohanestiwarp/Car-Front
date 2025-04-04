package com.carvajal.auth;

import com.carvajal.auth.service.JwtUtilService;
import com.carvajal.http.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private ReactiveUserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/authenticate")
    public Mono<ResponseEntity<Map<String, Object>>> login(@RequestBody AuthenticationReq loginRequest) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());

        return authenticationManager.authenticate(authToken)
                .flatMap(auth -> {
                    UserDetails userDetails = (UserDetails) auth.getPrincipal();
                    String jwt = jwtUtilService.generateToken(userDetails);

                    logger.info("Auth: client {} authenticated", userDetails.getUsername());

                    return Mono.just(ResponseHandler.success("Success", new TokenInfo(jwt)));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Credenciales inválidas"))));
    }
}
