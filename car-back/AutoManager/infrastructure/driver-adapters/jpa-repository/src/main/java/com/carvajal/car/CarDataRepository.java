package com.carvajal.car;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CarDataRepository extends ReactiveCrudRepository<CarData, Long> {

    @Modifying
    @Query("UPDATE cars SET brand = :#{#car.brand}, model = :#{#car.model}, year = :#{#car.year}, plate = :#{#car.plate}, color = :#{#car.color} WHERE id = :#{#car.id}")
    Mono<Integer> update(CarData car);

    @Query("SELECT * FROM cars " +
            "WHERE user_id = :userId " +
            "AND (:plate IS NULL OR plate = :plate) " +
            "AND (:model IS NULL OR model = :model) " +
            "AND (:brand IS NULL OR brand = :brand) " +
            "AND (:year IS NULL OR year = :year)")
    Flux<CarData> findByUserId(Long userId, String plate, String model, String brand, Integer year);

    Mono<CarData> findByPlate(String plate);

    @Query("SELECT * FROM cars WHERE id = :id AND user_id = :userId")
    Mono<CarData> findByIdAndUserId(Long id, Long userId);

    Mono<Integer> deleteByIdAndUserId(Long id, Long userId);
}