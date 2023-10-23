package com.skylabng.jaizexpress.enums;

import lombok.Getter;

@Getter
public enum SubsidyStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    SUSPENDED("Suspended");

    private final String statusName;

    SubsidyStatus(String statusName) {
        this.statusName = statusName;
    }
}
