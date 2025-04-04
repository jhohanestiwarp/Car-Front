package com.carvajal.car.properties;

import com.carvajal.commons.ValidateData;

public class Brand {
    private static String FIELD_NAME = "brand";
    private String value;

    public Brand(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}
