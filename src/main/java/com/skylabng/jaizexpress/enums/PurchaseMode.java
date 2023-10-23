package com.skylabng.jaizexpress.enums;

import lombok.Getter;

@Getter
public enum PurchaseMode {
    APP("App"),
    AGENT("Agent"),
    CARD("Card");

    private final String modeName;

    PurchaseMode(String modeName){
        this.modeName = modeName;
    }
}
