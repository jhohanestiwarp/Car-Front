package com.carvajal.helpers;

public class ConvertString {
    public static String slug(String value) {
        return value.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}
