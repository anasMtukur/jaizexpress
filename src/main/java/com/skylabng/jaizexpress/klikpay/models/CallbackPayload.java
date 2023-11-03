package com.skylabng.jaizexpress.klikpay.models;

public record CallbackPayload(
        String status,
        String amount,
        String currency,
        String trace_id,
        String reference_id
) {
}
