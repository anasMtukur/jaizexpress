package com.skylabng.jaizexpress.subsidy;

import com.skylabng.jaizexpress.enums.SubsidyStatus;

import java.util.Date;
import java.util.UUID;

public record SubsidyPayload(
        UUID id,
        UUID serviceId,
        String title,
        String description,
        double discount,
        SubsidyStatus status,
        Date createdAt,
        Date updatedAt

) {
}
