package com.skylabng.jaizexpress.payload;

import com.skylabng.jaizexpress.enums.TransactionType;

public record CardTransactionPayload(
        String number,
        double amount,
        TransactionType transactionType
) {
}
