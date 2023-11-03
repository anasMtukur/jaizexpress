package com.skylabng.jaizexpress.card;

import java.util.List;
import java.util.UUID;

public interface CardInternalAPI {
    CardPayload save( CardPayload card );

    List<CardPayload> getUserCardList(UUID userId );

    CardPayload getCardByNumber( String number );
}
