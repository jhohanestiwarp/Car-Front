package com.carvajal.car.properties;

import com.carvajal.commons.ValidateData;

public class Color {
    private static String FIELD_NAME = "color";
    private String value;

    public Color(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}