package com.carvajal.client.properties;

import com.carvajal.commons.ValidateData;

public class FullName {
    private static String FIELD_NAME = "fullName";
    private String value;

    public FullName(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}
