package com.skylabng.jaizexpress.transaction;

import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionInternalAPI {
    TransactionPayload save( TransactionPayload payload );

    PagedPayload<TransactionPayload> getTransactionList(Pageable pageable);

    PagedPayload<TransactionPayload> getUserTransactionList(UUID userId, Pageable pageable);

    PagedPayload<TransactionPayload> getCardTransactionList(String cardNumber, Pageable pageable);

    TransactionPayload getTransactionById( UUID id );

    boolean balanceTransaction(TransactionPayload transaction );
}
