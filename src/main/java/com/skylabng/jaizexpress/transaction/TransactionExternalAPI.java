package com.skylabng.jaizexpress.transaction;

import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionExternalAPI {
    TransactionPayload initiateTransaction( TransactionPayload payload );

    TransactionPayload updateTransaction( TransactionPayload payload );

    PagedPayload<TransactionPayload> getTransactionList(Pageable pageable);

    PagedPayload<TransactionPayload> getUserTransactionList(UUID userId, Pageable pageable);

    PagedPayload<TransactionPayload> getCardTransactionList(String cardNumber, Pageable pageable);
}
