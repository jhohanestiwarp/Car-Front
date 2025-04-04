package com.carvajal.car.services;

import com.carvajal.car.Car;
import com.carvajal.car.dto.CarDto;
import com.carvajal.car.gatewey.in.CarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService implements CarUseCase {
    private final CarUseCase carUseCase;

    @Override
    public Mono<Car> createCar(Car car, Long userId) {
        return carUseCase.createCar(car, userId);
    }

    @Override
    public Flux<CarDto> listCarsByUser(Long userId, String plate, String model, String brand, Integer year) {
        return carUseCase.listCarsByUser(userId, plate, model, brand, year);
    }

    @Override
    public Mono<CarDto> updateCar(Long carId, Long userId, Car car) {
        return carUseCase.updateCar(userId, carId, car);
    }

    @Override
    public Mono<Boolean> deleteCar(Long carId, Long userId) {
        return carUseCase.deleteCar(carId, userId);
    }
}