package com.skylabng.jaizexpress.wallet.repository;

import com.skylabng.jaizexpress.card.CardPayload;
import com.skylabng.jaizexpress.wallet.WalletPayload;
import com.skylabng.jaizexpress.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository  extends JpaRepository<Wallet, UUID> {
    Optional<WalletPayload> findWalletById(UUID id);

    Optional<WalletPayload> findFirstWalletByUserId(UUID userId);
}
