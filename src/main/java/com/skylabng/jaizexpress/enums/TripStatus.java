package com.skylabng.jaizexpress.enums;

import lombok.Getter;

@Getter
public enum TripStatus {
    NEW("New"),
    VALID("Valid"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed");

    private final String statusName;

    TripStatus(String statusName){
        this.statusName = statusName;
    }
}
