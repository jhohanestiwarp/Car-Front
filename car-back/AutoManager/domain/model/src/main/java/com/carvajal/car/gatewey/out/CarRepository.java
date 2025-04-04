package com.carvajal.car.gatewey.out;

import com.carvajal.car.Car;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarRepository {
    Mono<Car> save(Car car);
    Mono<Car> update(Car car);
    Flux<Car> findByUserId(Long userId, String plate, String model, String brand, Integer year);
    Mono<Car> findByPlate(String plate);
    Mono<Car> findByIdAndUserId(Long id, Long userId);
    Mono<Boolean> deleteByIdAndUserId(Long id, Long userId);
}
