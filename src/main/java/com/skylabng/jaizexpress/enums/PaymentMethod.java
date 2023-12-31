package com.skylabng.jaizexpress.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH("Cash"),
    CARD("Card"),
    QR("QR Code");

    private final String methodName;

    PaymentMethod(String methodName){
        this.methodName = methodName;
    }
}
