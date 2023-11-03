package com.skylabng.jaizexpress.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skylabng.jaizexpress.card.CardPayload;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.wallet.WalletPayload;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDetailsPayload(
        EndUserPayload userDetails,
        WalletPayload wallet,
        List<CardPayload> cards
) {
}
