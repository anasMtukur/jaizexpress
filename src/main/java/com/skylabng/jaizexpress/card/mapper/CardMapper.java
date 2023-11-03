package com.skylabng.jaizexpress.card.mapper;

import com.skylabng.jaizexpress.card.CardPayload;
import com.skylabng.jaizexpress.card.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {
    Card fromPayloadToCard(CardPayload payload );

    CardPayload fromCardToPayload( Card card );
}
