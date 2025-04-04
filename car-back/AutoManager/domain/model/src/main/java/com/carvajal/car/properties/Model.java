package com.carvajal.car.properties;

import com.carvajal.commons.ValidateData;

public class Model {
    private static String FIELD_NAME = "model";
    private String value;

    public Model(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}
