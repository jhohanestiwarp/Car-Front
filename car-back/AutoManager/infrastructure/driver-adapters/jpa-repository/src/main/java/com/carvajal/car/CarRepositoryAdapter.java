package com.carvajal.car;

import com.carvajal.car.gatewey.out.CarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CarRepositoryAdapter implements CarRepository {
    private final CarDataRepository repository;
    private final CarMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(CarRepositoryAdapter.class);

    @Override
    public Mono<Car> save(Car car) {
        return mapper.toNewEntityData(car)
                .flatMap(repository::save)
                .map(mapper::toDomainDtoModel)
                .onErrorResume(Exception.class, e -> {
                    logger.error("Unexpected error: {}", e.getMessage());
                    return Mono.error(new IllegalArgumentException(e));
                });
    }

    @Override
    public Mono<Car> update(Car car) {
        return mapper.toEntityData(car)
                .flatMap(carData -> repository.update(carData)
                        .flatMap(rowsUpdated -> {
                            if (rowsUpdated > 0) {
                                return Mono.just(car);
                            } else {
                                return Mono.error(new IllegalArgumentException("No se pudo actualizar el auto"));
                            }
                        })
                )
                .onErrorResume(Exception.class, e -> {
                    logger.error("Unexpected error: {}", e.getMessage());
                    return Mono.error(new IllegalArgumentException(e));
                });
    }

    @Override
    public Flux<Car> findByUserId(Long userId, String plate, String model, String brand, Integer year) {
        return repository.findByUserId(userId, plate, model, brand, year).map(mapper::toDomainDtoModel);
    }

    @Override
    public Mono<Car> findByPlate(String plate) {
        return repository.findByPlate(plate).map(mapper::toDomainDtoModel);
    }

    @Override
    public Mono<Car> findByIdAndUserId(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId).map(mapper::toDomainDtoModel);
    }

    @Override
    public Mono<Boolean> deleteByIdAndUserId(Long id, Long userId) {
        logger.info("Calling DELETE for id={} and userId={}", id, userId);
        return repository.deleteByIdAndUserId(id, userId)
                .map(rows -> {
                    logger.info("Rows affected: {}", rows);
                    return rows > 0;
                });
    }
}