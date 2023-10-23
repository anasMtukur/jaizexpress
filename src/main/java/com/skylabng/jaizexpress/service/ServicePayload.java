package com.skylabng.jaizexpress.service;

import com.skylabng.jaizexpress.enums.NigerianState;
import com.skylabng.jaizexpress.enums.ServiceStatus;
import com.skylabng.jaizexpress.enums.ServiceType;

import java.util.Date;
import java.util.UUID;

public record ServicePayload(
        UUID id,
        UUID provider,
        String logo,
        ServiceType serviceType,
        String title,
        String description,
        ServiceStatus status,
        NigerianState state,
        Date createdAt,
        Date updatedAt
) {
}
