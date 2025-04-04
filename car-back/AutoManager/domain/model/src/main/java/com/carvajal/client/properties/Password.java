package com.carvajal.client.properties;

import com.carvajal.commons.ValidateData;

public class Password {
    private static String FIELD_NAME = "password";
    private String value;

    public Password(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}
