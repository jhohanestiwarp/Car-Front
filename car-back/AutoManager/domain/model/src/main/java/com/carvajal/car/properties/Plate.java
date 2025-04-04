package com.carvajal.car.properties;

import com.carvajal.commons.ValidateData;

public class Plate {
    private static final String FIELD_NAME = "plate";
    private static final String PATTERN = "^[A-Z]{3}-(\\d{3}|\\d{2}[A-Z])$";

    private final String value;

    public Plate(String value) {
        if (!ValidateData.string(value, FIELD_NAME) || !value.matches(PATTERN)) {
            throw new IllegalArgumentException("La placa debe tener formato válido (ej. ABC-123 o ABC-1234)");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}