package com.example.manage_coffeeshop_dataservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");

    private final String displayName;

    Gender(String displayName){
        this.displayName = displayName;
    }
    @JsonValue
    public String getDisplayName(){
        return displayName;
    }
    @JsonCreator
    public static Gender fromDisplayName(String displayName) {
        for (Gender g : Gender.values()) {
            if (g.displayName.equalsIgnoreCase(displayName)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Unknown gender: " + displayName);
    }

}
