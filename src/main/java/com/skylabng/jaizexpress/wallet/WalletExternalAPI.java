package com.skylabng.jaizexpress.wallet;

import com.skylabng.jaizexpress.enums.TransactionType;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.payload.ReloadWalletPayload;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WalletExternalAPI {
    PagedPayload<WalletPayload> getPagedList(Pageable pageable);
    WalletPayload getWalletById( UUID id );
    WalletPayload save( WalletPayload wallet );

    WalletPayload reload( ReloadWalletPayload payload );

    boolean updateBalance( UUID walletId, double amount, TransactionType type );
}
