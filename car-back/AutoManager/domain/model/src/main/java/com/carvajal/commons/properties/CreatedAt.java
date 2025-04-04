package com.carvajal.commons.properties;

import com.carvajal.commons.ValidateData;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CreatedAt {
    private static String FIELD_NAME = "createdAt";
    private LocalDateTime value;

    public CreatedAt(LocalDateTime value) {
        if(ValidateData.date(value, FIELD_NAME)){
            this.value = value;
        }
    }

    public LocalDateTime getValue(){ return value; }
}
