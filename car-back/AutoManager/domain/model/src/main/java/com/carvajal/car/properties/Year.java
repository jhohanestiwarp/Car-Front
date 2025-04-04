package com.carvajal.car.properties;

import com.carvajal.commons.ValidateData;

import java.time.LocalDate;

public class Year {
    private static final String FIELD_NAME = "year";

    private final Integer value;

    public Year(Integer value) {
        if (!ValidateData.number(value, FIELD_NAME)) {
            throw new IllegalArgumentException("El año no puede ser nulo");
        }

        int currentYear = LocalDate.now().getYear();
        if (value > currentYear) {
            throw new IllegalArgumentException("El año no puede ser mayor al actual");
        }

        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}