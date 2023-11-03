package com.skylabng.jaizexpress.card;

import com.skylabng.jaizexpress.enums.TransactionType;
import com.skylabng.jaizexpress.payload.CardDetailsPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CardExternalAPI {
    PagedPayload<CardPayload> getCardList(Pageable pageable);
    CardDetailsPayload getCardDetails(UUID id );
    List<CardPayload> getUserCardList(UUID userId );
    CardPayload getCardByNumber( String number );
    CardPayload save( CardPayload card );

    boolean updateBalance(UUID walletId, double amount, TransactionType type);
}
