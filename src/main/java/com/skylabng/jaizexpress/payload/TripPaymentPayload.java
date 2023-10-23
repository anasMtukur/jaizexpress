package com.skylabng.jaizexpress.payload;

import com.skylabng.jaizexpress.enums.PaymentMethod;
import com.skylabng.jaizexpress.enums.PaymentStatus;

public record TripPaymentPayload(
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus
) {
}
