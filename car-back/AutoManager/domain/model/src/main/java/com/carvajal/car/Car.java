package com.carvajal.car;

import com.carvajal.car.properties.*;
import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {
    private Id id;
    private Brand brand;
    private Model model;
    private Year year;
    private Plate plate;
    private Photo photo;
    private Color color;
    private Id userId;

    public Car(Id id, Brand brand, Model model, Year year, Plate plate, Photo photo, Color color, Id userId) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.plate = plate;
        this.photo = photo;
        this.color = color;
        this.userId = userId;
    }
}