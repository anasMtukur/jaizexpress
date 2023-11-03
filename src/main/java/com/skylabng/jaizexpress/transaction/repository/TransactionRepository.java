package com.skylabng.jaizexpress.transaction.repository;

import com.skylabng.jaizexpress.transaction.TransactionPayload;
import com.skylabng.jaizexpress.transaction.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findByUserId(UUID userId, Pageable pageable);
    Page<Transaction> findByCard(String card, Pageable pageable);
    Optional<Transaction> findTransactionById(UUID id);
}
