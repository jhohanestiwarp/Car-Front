package com.carvajal.client.properties;

import com.carvajal.commons.ValidateData;

public class Email {
    private static String FIELD_NAME = "email";
    private String value;

    public Email(String value) {
        if(ValidateData.string(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public String getValue(){ return value; }
}
