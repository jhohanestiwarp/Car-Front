package com.carvajal.car.dto;

import com.carvajal.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long id;
    private Long userId;
    private String brand;
    private String model;
    private Integer year;
    private String plate;
    private String photo;
    private String color;

    public static CarDto fromDomain(Car car) {
        return new CarDto(
                car.getId().getValue(),
                car.getUserId().getValue(),
                car.getBrand().getValue(),
                car.getModel().getValue(),
                car.getYear().getValue(),
                car.getPlate().getValue(),
                car.getPhoto() != null ? car.getPhoto().getValue() : null,
                car.getColor().getValue()
        );
    }
}