package com.skylabng.jaizexpress.payment;

import com.skylabng.jaizexpress.enums.PaymentMethod;
import com.skylabng.jaizexpress.enums.PaymentStatus;

import java.util.Date;
import java.util.UUID;

public record PaymentPayload(
        UUID id,
        UUID transaction,
        double amount,
        String ref,
        PaymentMethod method,
        String message,
        String url,
        PaymentStatus status,
        Date paymentDate,
        Date createdAt,
        Date updatedAt
) {

    public PaymentPayload withKlikResponse( String ref, String message, String url, PaymentStatus status, Date paymentDate ){
        return new PaymentPayload(
                id,
                transaction,
                amount,
                ref,
                method,
                message,
                url,
                status,
                paymentDate,
                createdAt,
                updatedAt
        );
    }

    public PaymentPayload withCallback( String ref, PaymentStatus status, Date paymentDate ){
        return new PaymentPayload(
                id,
                transaction,
                amount,
                ref,
                method,
                message,
                url,
                status,
                paymentDate,
                createdAt,
                updatedAt
        );
    }
}
