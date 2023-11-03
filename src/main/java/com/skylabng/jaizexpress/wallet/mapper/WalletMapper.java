package com.skylabng.jaizexpress.wallet.mapper;

import com.skylabng.jaizexpress.wallet.WalletPayload;
import com.skylabng.jaizexpress.wallet.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface WalletMapper {
    Wallet toWallet(WalletPayload payload );
    WalletPayload toPayload( Wallet wallet );
}
