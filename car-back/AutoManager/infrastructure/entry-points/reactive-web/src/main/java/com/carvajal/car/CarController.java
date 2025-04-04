package com.carvajal.car;

import com.carvajal.client.services.ClientService;
import com.carvajal.http.ResponseHandler;
import com.carvajal.car.services.CarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;
    private final ClientService clientService;

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> createCar(@RequestBody Car request) {
        logger.info("Car: creating new car");

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(clientService::getClientByEmail)
                .flatMap(client -> carService.createCar(request, client.getId().getValue()))
                .map(car -> ResponseHandler.success(HttpStatus.CREATED, "Carro creado"))
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.warn("Validation failed: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error(e.getMessage(), HttpStatus.BAD_REQUEST));
                })
                .onErrorResume(IllegalStateException.class, e -> {
                    logger.warn("Conflict: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error(e.getMessage(), HttpStatus.CONFLICT));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.error("Unexpected error: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> listCars(
            @RequestParam(required = false) String plate,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer year
    ) {
        logger.info("Car: get all");

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(clientService::getClientByEmail)
                .flatMapMany(client -> carService.listCarsByUser(client.getId().getValue(), plate, model, brand, year))
                .collectList()
                .map(carList -> ResponseHandler.success("Success", carList))
                .switchIfEmpty(Mono.just(ResponseHandler.success("Carro no encontrado")))
                .onErrorResume(Exception.class, e -> {
                    logger.error("Unexpected error: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> updateCar(@PathVariable Long id, @RequestBody Car request) {
        logger.info("Car: updating {}", id);

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(clientService::getClientByEmail)
                .flatMap(client -> carService.updateCar(id, client.getId().getValue(), request))
                .map(car -> ResponseHandler.success("Carro actualizado", car))
                .switchIfEmpty(Mono.just(ResponseHandler.error("Carro no encontrado", HttpStatus.NOT_FOUND)))
                .onErrorResume(IllegalArgumentException.class, e -> {
                    logger.warn("Validation error: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error(e.getMessage(), HttpStatus.BAD_REQUEST));
                })
                .onErrorResume(IllegalStateException.class, e -> {
                    logger.warn("Conflict: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error(e.getMessage(), HttpStatus.CONFLICT));
                })
                .onErrorResume(Exception.class, e -> {
                    logger.error("Unexpected error: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> deleteCar(@PathVariable Long id) {
        logger.info("Car: deleting {}", id);

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(clientService::getClientByEmail)
                .flatMap(client -> carService.deleteCar(id, client.getId().getValue())
                        .flatMap(deleted -> {
                            if (Boolean.TRUE.equals(deleted)) {
                                return Mono.just(ResponseHandler.success("Carro eliminado"));
                            } else {
                                return Mono.just(ResponseHandler.error("Carro no encontrado", HttpStatus.NOT_FOUND));
                            }
                        }))
                .onErrorResume(e -> {
                    logger.error("Unexpected error: {}", e.getMessage());
                    return Mono.just(ResponseHandler.error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }
}