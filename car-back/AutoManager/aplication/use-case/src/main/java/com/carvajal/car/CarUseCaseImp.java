package com.carvajal.car;

import com.carvajal.car.dto.CarDto;
import com.carvajal.car.gatewey.in.CarUseCase;
import com.carvajal.car.gatewey.out.CarRepository;
import com.carvajal.car.properties.*;
import com.carvajal.commons.properties.Id;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CarUseCaseImp implements CarUseCase {
    private final CarRepository carRepository;

    private static final Logger logger = LoggerFactory.getLogger(CarUseCaseImp.class);

    @Override
    public Mono<Car> createCar(Car car, Long userId) {
        car.setUserId(new Id(userId));
        return carRepository.findByPlate(car.getPlate().getValue())
                .flatMap(existing -> Mono.<Car>error(new IllegalArgumentException("Ya existe un auto con esa placa")))
                .switchIfEmpty(carRepository.save(car));
    }

    @Override
    public Flux<CarDto> listCarsByUser(Long userId, String plate, String model, String brand, Integer year) {
        return carRepository.findByUserId(userId, plate, model, brand, year)
                .map(CarDto::fromDomain);
    }

    @Override
    public Mono<CarDto> updateCar(Long userId, Long carId, Car car) {
        return carRepository.findByPlate(car.getPlate().getValue())
                .flatMap(existingPlate -> {
                    if (existingPlate != null && !existingPlate.getId().getValue().equals(carId)) {
                        return Mono.error(new IllegalArgumentException("Ya existe un auto con esa placa"));
                    }
                    return Mono.empty();
                })
                .then(carRepository.findByIdAndUserId(carId, userId)
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("El auto no fue encontrado")))
                        .flatMap(existing -> {
                            existing.setBrand(new Brand(car.getBrand().getValue()));
                            existing.setModel(new Model(car.getModel().getValue()));
                            existing.setYear(new Year(car.getYear().getValue()));
                            existing.setPlate(new Plate(car.getPlate().getValue()));
                            existing.setColor(new Color(car.getColor().getValue()));
                            return carRepository.update(existing); // debe devolver Mono<Car>
                        })
                )
                .map(CarDto::fromDomain);
    }

    @Override
    public Mono<Boolean> deleteCar(Long carId, Long userId) {
        return carRepository.deleteByIdAndUserId(carId, userId);
    }
}