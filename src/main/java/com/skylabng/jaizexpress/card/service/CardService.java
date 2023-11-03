package com.skylabng.jaizexpress.card.service;

import com.skylabng.jaizexpress.card.CardExternalAPI;
import com.skylabng.jaizexpress.card.CardInternalAPI;
import com.skylabng.jaizexpress.card.CardPayload;
import com.skylabng.jaizexpress.card.mapper.CardMapper;
import com.skylabng.jaizexpress.card.model.Card;
import com.skylabng.jaizexpress.card.repository.CardRepository;
import com.skylabng.jaizexpress.enums.*;
import com.skylabng.jaizexpress.payload.CardDetailsPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.transaction.TransactionInternalAPI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardService implements CardInternalAPI, CardExternalAPI {
    private final CardRepository repository;
    private final CardMapper mapper;

    public CardService(CardRepository repository, CardMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PagedPayload<CardPayload> getCardList(Pageable pageable) {
        Page<Card> page = this.repository.findAll(pageable);
        List<CardPayload> cards = page.getContent()
                .stream()
                .map(mapper::fromCardToPayload)
                .toList();

        return new PagedPayload<CardPayload>(cards, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public CardDetailsPayload getCardDetails(UUID id) {
        CardPayload card = this.repository.getCardById( id );
        return new CardDetailsPayload( card );
    }

    @Override
    public List<CardPayload> getUserCardList(UUID userId) {
        return this.repository.findCardByUserId( userId );
    }

    @Override
    public CardPayload getCardByNumber(String number) {
        return this.repository.findCardByNumber( number )
                .orElseThrow(()->new RuntimeException("Card with given number not found."));
    }

    @Override
    public CardPayload save(CardPayload card) {
        return mapper.fromCardToPayload(
                repository.save(mapper.fromPayloadToCard(card)));
    }

    @Override
    public boolean updateBalance(UUID cardId, double amount, TransactionType type) {
        CardPayload card = repository.findCardById( cardId ).orElseThrow(
                ()-> new RuntimeException( "User Card Not Found" ));

        if( type.equals( TransactionType.DR ) && canCompleteDebit( card, amount ) ){
            throw new RuntimeException( "Cannot complete transaction. Card Balance is low." );
        }

        double newBalance = calculateNewAmount( card.balance(), amount, type );
        CardPayload entity = card.withBalance( newBalance );

        mapper.fromCardToPayload(
                repository.save( mapper.fromPayloadToCard( entity ) ) );

        entity = repository.findCardById( cardId ).orElseThrow(
                ()-> new RuntimeException( "Card Not Found" ));

        return entity.balance() == newBalance;
    }

    private double calculateNewAmount(double balance, double amount, TransactionType transaction) {
        if( transaction.equals( TransactionType.CR ) ){
            return balance + amount;
        }

        return balance - amount;
    }

    private static boolean canCompleteDebit( CardPayload card, double amount ){

        if( !CardStatus.ACTIVE.equals( card.status() )){
            return false;
        }

        double standingBalance = card.balance();
        return !(amount > standingBalance);
    }
}
