package com.skylabng.jaizexpress.payload;

import com.skylabng.jaizexpress.enums.TransactionType;

import java.util.UUID;

public record ReloadWalletPayload(
        UUID walletId,
        double amount

) {
}
