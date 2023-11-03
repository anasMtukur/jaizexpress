package com.skylabng.jaizexpress.payment;

import com.skylabng.jaizexpress.transaction.TransactionPayload;

import java.util.UUID;

public interface PaymentInternalAPI {
    PaymentPayload getTransactionPayment( UUID transactionId );
    PaymentPayload getPaymentByID( UUID id );
    PaymentPayload initiatePayment(TransactionPayload transaction ) throws Exception;
    PaymentPayload save( PaymentPayload payment );
}
