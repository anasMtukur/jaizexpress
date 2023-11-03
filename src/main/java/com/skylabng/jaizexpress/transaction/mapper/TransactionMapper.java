package com.skylabng.jaizexpress.transaction.mapper;

import com.skylabng.jaizexpress.transaction.TransactionPayload;
import com.skylabng.jaizexpress.transaction.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    Transaction toModel( TransactionPayload payload );
    TransactionPayload toPayload( Transaction model );
}
