package com.carvajal.car.gatewey.in;

import com.carvajal.car.Car;
import com.carvajal.car.dto.CarDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CarUseCase {
    Mono<Car> createCar(Car car, Long userId);
    Flux<CarDto> listCarsByUser(Long userId, String plate, String model, String brand, Integer year);
    Mono<CarDto> updateCar(Long userId, Long carId, Car car);
    Mono<Boolean> deleteCar(Long carId, Long userId);
}