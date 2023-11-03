package com.skylabng.jaizexpress.klikpay.models;

import java.util.List;

public record CheckoutPayload(
        double amount,
        String currency,
        String reference_id,
        String callback_url,
        String redirect_url,
        String first_name,
        String last_name,
        List<PayloadItem> payload
) {
}
