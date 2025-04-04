package com.carvajal.car.properties;

public class Photo {
    private static String FIELD_NAME = "photo";
    private String value;

    public Photo(String value) {
        // Can be null
        this.value = value;
    }

    public String getValue(){ return value; }
}
