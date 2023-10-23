package com.skylabng.jaizexpress.enums;

import lombok.Getter;

@Getter
public enum NigerianState {
    ABIA("Abia"),
    ADAMAWA("Adamawa"),
    AKWA_IBOM("Akwa Ibom"),
    ANAMBRA("Anambra"),
    BAUCHI("Bauchi"),
    BAYELSA("Bayelsa"),
    BENUE("Benue"),
    BORNO("Borno"),
    CROSS_RIVER("Cross River"),
    DELTA("Delta"),
    EBONYI("Ebonyi"),
    EDO("Edo"),
    EKITI("Ekiti"),
    ENUGU("Enugu"),
    GOMBE("Gombe"),
    IMO("Imo"),
    JIGAWA("Jigawa"),
    KADUNA("Kaduna"),
    KANO("Kano"),
    KATSINA("Katsina"),
    KEBBI("Kebbi"),
    KOGI("Kogi"),
    KWARA("Kwara"),
    LAGOS("Lagos"),
    NASARAWA("Nasarawa"),
    NIGER("Niger"),
    OGUN("Ogun"),
    ONDO("Ondo"),
    OSUN("Osun"),
    OYO("Oyo"),
    PLATEAU("Plateau"),
    RIVERS("Rivers"),
    SOKOTO("Sokoto"),
    TARABA("Taraba"),
    YOBE("Yobe"),
    ZAMFARA("Zamfara"),
    FCT_ABUJA("Federal Capital Territory (Abuja)");

    private final String stateName;

    NigerianState(String stateName) {
        this.stateName = stateName;
    }

}
