package com.skylabng.jaizexpress.klikpay.callbacklog;

import java.util.Date;
import java.util.UUID;

public record CallbackLogPayload(
        UUID id,
        String status,
        String amount,
        String currency,
        String traceId,
        String referenceId,
        Date createdAt,
        Date updatedAt
) {
}
