package com.carvajal.car;

import com.carvajal.car.properties.*;
import com.carvajal.commons.properties.Id;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class CarMapper {
    public Mono<CarData> toEntityData(Car car) {
        return Mono.just(CarData.builder()
                .id(car.getId().getValue())
                .brand(car.getBrand().getValue())
                .model(car.getModel().getValue())
                .year(car.getYear().getValue())
                .plate(car.getPlate().getValue())
                .photo(Optional.ofNullable(car.getPhoto()).map(Photo::getValue).orElse(null))
                .color(car.getColor().getValue())
                .userId(car.getUserId().getValue())
                .build());
    }

    public Mono<CarData> toNewEntityData(Car car) {
        return Mono.just(CarData.builder()
                .brand(car.getBrand().getValue())
                .model(car.getModel().getValue())
                .year(car.getYear().getValue())
                .plate(car.getPlate().getValue())
                .photo(Optional.ofNullable(car.getPhoto()).map(Photo::getValue).orElse(null))
                .color(car.getColor().getValue())
                .userId(car.getUserId().getValue())
                .build());
    }

    public Car toDomainDtoModel(CarData data) {
        return new Car(
                new Id(data.getId()),
                new Brand(data.getBrand()),
                new Model(data.getModel()),
                new Year(data.getYear()),
                new Plate(data.getPlate()),
                data.getPhoto() != null ? new Photo(data.getPhoto()) : null,
                new Color(data.getColor()),
                new Id(data.getUserId())
        );
    }
}
