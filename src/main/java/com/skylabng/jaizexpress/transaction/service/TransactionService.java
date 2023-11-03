package com.skylabng.jaizexpress.transaction.service;

import com.skylabng.jaizexpress.enums.TransactionStatus;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.transaction.TransactionExternalAPI;
import com.skylabng.jaizexpress.transaction.TransactionInternalAPI;
import com.skylabng.jaizexpress.transaction.TransactionPayload;
import com.skylabng.jaizexpress.transaction.mapper.TransactionMapper;
import com.skylabng.jaizexpress.transaction.model.Transaction;
import com.skylabng.jaizexpress.transaction.repository.TransactionRepository;
import com.skylabng.jaizexpress.wallet.WalletInternalAPI;
import com.skylabng.jaizexpress.wallet.WalletPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService implements TransactionInternalAPI, TransactionExternalAPI {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final WalletInternalAPI walletAPI;

    public TransactionService(TransactionRepository repository, TransactionMapper mapper, WalletInternalAPI walletAPI) {
        this.repository = repository;
        this.mapper = mapper;
        this.walletAPI = walletAPI;
    }

    @Override
    public TransactionPayload initiateTransaction(TransactionPayload payload) {
        return mapper.toPayload(
                this.repository.save( mapper.toModel(payload) ));
    }

    @Override
    public TransactionPayload updateTransaction(TransactionPayload payload) {
        return mapper.toPayload(
                this.repository.save( mapper.toModel(payload) ));
    }

    @Override
    public TransactionPayload save(TransactionPayload payload) {
        return mapper.toPayload(
                this.repository.save( mapper.toModel(payload) ));
    }

    @Override
    public PagedPayload<TransactionPayload> getTransactionList(Pageable pageable) {
        Page<Transaction> page = this.repository.findAll(pageable);
        List<TransactionPayload> list = mapListToPayload( page.getContent() );

        return new PagedPayload<TransactionPayload>(list, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public PagedPayload<TransactionPayload> getUserTransactionList(UUID userId, Pageable pageable) {
        Page<Transaction> page = this.repository.findByUserId(userId, pageable);
        List<TransactionPayload> list = mapListToPayload( page.getContent() );

        return new PagedPayload<TransactionPayload>(list, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public PagedPayload<TransactionPayload> getCardTransactionList(String cardNumber, Pageable pageable) {
        Page<Transaction> page = this.repository.findByCard(cardNumber, pageable);
        List<TransactionPayload> list = mapListToPayload( page.getContent() );

        return new PagedPayload<TransactionPayload>(list, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public TransactionPayload getTransactionById(UUID id) {
        Transaction transaction = repository.findTransactionById( id ).orElseThrow(
                ()->new RuntimeException( "Transaction with the given ID is not found." ));

        return mapper.toPayload( transaction );
    }

    @Override
    public boolean balanceTransaction(TransactionPayload transaction) {
        boolean result = false;

        WalletPayload wallet = walletAPI.getUserWallet( transaction.getUserId() );
        boolean walletUpdated = walletAPI.updateBalance( wallet.id(), transaction.getAmount(), transaction.getType() );

        transaction.setStatus( TransactionStatus.COMPLETED );
        transaction.setBalanced( walletUpdated );
        transaction = this.save( transaction );

        return transaction.isBalanced();
    }

    private List<TransactionPayload> mapListToPayload(List<Transaction> transactions ){
        return transactions
                .stream()
                .map(mapper::toPayload)
                .toList();
    }

}
