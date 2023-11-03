package com.skylabng.jaizexpress.wallet.service;

import com.skylabng.jaizexpress.enums.TransactionType;
import com.skylabng.jaizexpress.enums.WalletStatus;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.payload.ReloadWalletPayload;
import com.skylabng.jaizexpress.payment.PaymentPayload;
import com.skylabng.jaizexpress.payment.model.Payment;
import com.skylabng.jaizexpress.wallet.WalletExternalAPI;
import com.skylabng.jaizexpress.wallet.WalletInternalAPI;
import com.skylabng.jaizexpress.wallet.WalletPayload;
import com.skylabng.jaizexpress.wallet.mapper.WalletMapper;
import com.skylabng.jaizexpress.wallet.model.Wallet;
import com.skylabng.jaizexpress.wallet.repository.WalletRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService implements WalletExternalAPI, WalletInternalAPI {
    private final WalletRepository repository;
    private final WalletMapper mapper;

    public WalletService(WalletRepository repository, WalletMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public PagedPayload<WalletPayload> getPagedList(Pageable pageable) {
        Page<Wallet> page = this.repository.findAll(pageable);
        List<WalletPayload> list = page.getContent().stream().map(mapper::toPayload).toList();

        return new PagedPayload<WalletPayload>(list, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public WalletPayload getWalletById(UUID id) {
        return repository.findWalletById( id ).orElseThrow(
                ()-> new RuntimeException( "User Wallet Not Found" ));
    }

    @Override
    public WalletPayload save(WalletPayload wallet) {
        return mapper.toPayload(
                repository.save( mapper.toWallet( wallet ) ) );
    }

    @Override
    public WalletPayload getUserWallet(UUID userId) {
        return repository.findFirstWalletByUserId( userId ).orElseThrow(
                ()-> new RuntimeException( "User Wallet Not Found" ));
    }

    @Override
    public WalletPayload reload(ReloadWalletPayload payload) {
        //Transaction
        return null;
    }

    @Override
    public boolean updateBalance(UUID walletId, double amount, TransactionType type) {
        WalletPayload wallet = repository.findWalletById( walletId ).orElseThrow(
                ()-> new RuntimeException( "User Wallet Not Found" ));

        if( type.equals( TransactionType.DR ) && canCompleteDebit( wallet, amount ) ){
            throw new RuntimeException( "Cannot complete transaction. Wallet Balance is low." );
        }

        double newBalance = calculateNewAmount( wallet.balance(), amount, type );
        WalletPayload entity = wallet.withBalance( newBalance );

        mapper.toPayload(
                repository.save( mapper.toWallet( entity ) ) );

        entity = repository.findWalletById( walletId ).orElseThrow(
                ()-> new RuntimeException( "Wallet Not Found" ));

        return entity.balance() == newBalance;
    }

    private double calculateNewAmount(double balance, double amount, TransactionType transaction) {
        if( transaction.equals( TransactionType.CR ) ){
            return balance + amount;
        }

        return balance - amount;
    }

    private static boolean canCompleteDebit( WalletPayload wallet, double amount ){

        if( !WalletStatus.ACTIVE.equals( wallet.status() )){
            return false;
        }

        double standingBalance = wallet.balance();
        return !(amount > standingBalance);
    }
}
