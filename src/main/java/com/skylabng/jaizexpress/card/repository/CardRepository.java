package com.skylabng.jaizexpress.card.repository;

import com.skylabng.jaizexpress.card.CardPayload;
import com.skylabng.jaizexpress.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    CardPayload getCardById( UUID id );

    Optional<CardPayload> findCardById(UUID id);

    List<CardPayload> findCardByUserId(UUID userId);

    Optional<CardPayload> findCardByNumber(String number);
}
