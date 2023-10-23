package com.skylabng.jaizexpress.enums;

import lombok.Getter;

@Getter
public enum ServiceStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    SUSPENDED("Suspended");

    private final String statusName;

    ServiceStatus(String statusName) {
        this.statusName = statusName;
    }
}
