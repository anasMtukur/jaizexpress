package com.skylabng.jaizexpress.payment;

import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.transaction.TransactionPayload;
import org.springframework.data.domain.Pageable;

public interface PaymentExternalAPI {
    PagedPayload<PaymentPayload> getPaymentList(Pageable pageable);
}
