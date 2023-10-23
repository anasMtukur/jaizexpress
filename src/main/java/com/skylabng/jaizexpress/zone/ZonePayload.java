package com.skylabng.jaizexpress.zone;

import java.util.Date;
import java.util.UUID;

public record ZonePayload(
        UUID id,
        UUID serviceId,
        String name,
        double baseCharge,
        Date createdAt,
        Date updatedAt
) {
}
