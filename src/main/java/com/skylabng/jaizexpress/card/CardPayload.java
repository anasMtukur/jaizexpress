package com.skylabng.jaizexpress.card;

import com.skylabng.jaizexpress.enums.CardStatus;

import java.util.Date;
import java.util.UUID;

public record CardPayload(
        UUID id,
        UUID userId,
        String number,
        double balance,
        CardStatus status,
        Date createdAt,
        Date updatedAt
) {

    public CardPayload withUserId( UUID userId ){
        return new CardPayload(
            id,
            userId,
            number,
            balance,
            status,
            createdAt,
            updatedAt
        );
    }

    public CardPayload withUserIdAndBalance( UUID userId, double balance ){
        return new CardPayload(
                id,
                userId,
                number,
                balance,
                status,
                createdAt,
                updatedAt
        );
    }

    public CardPayload withBalance( double balance ){
        return new CardPayload(
                id,
                userId,
                number,
                balance,
                status,
                createdAt,
                updatedAt
        );
    }

    public CardPayload withStatus( CardStatus status ){
        return new CardPayload(
                id,
                userId,
                number,
                balance,
                status,
                createdAt,
                updatedAt
        );
    }
}
