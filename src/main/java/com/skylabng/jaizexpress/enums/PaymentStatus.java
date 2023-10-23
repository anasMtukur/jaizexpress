package com.skylabng.jaizexpress.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    WAITING("Waiting"),
    PAID("Paid"),
    FAILED("Failed"),
    CANCELLED("Cancelled");

    private final String statusName;

    PaymentStatus(String statusName){
        this.statusName = statusName;
    }
}
