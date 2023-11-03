package com.skylabng.jaizexpress.wallet;

import com.skylabng.jaizexpress.card.CardPayload;
import com.skylabng.jaizexpress.enums.TransactionType;
import com.skylabng.jaizexpress.payload.ReloadWalletPayload;

import java.util.List;
import java.util.UUID;

public interface WalletInternalAPI {
    WalletPayload save(WalletPayload wallet);
    WalletPayload getWalletById( UUID id );
    WalletPayload getUserWallet( UUID userId );
    boolean updateBalance( UUID walletId, double amount, TransactionType type );
}
