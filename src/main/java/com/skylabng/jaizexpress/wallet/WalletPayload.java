package com.skylabng.jaizexpress.wallet;

import com.skylabng.jaizexpress.enums.WalletStatus;

import java.util.Date;
import java.util.UUID;

public record WalletPayload(
        UUID id,
        UUID userId,
        double balance,
        WalletStatus status,
        Date createdAt,
        Date updatedAt
) {

    public WalletPayload withBalance( double balance ){
        return new WalletPayload(
                id,
                userId,
                balance,
                status,
                createdAt,
                updatedAt
        );
    }
}
