package com.skylabng.jaizexpress.payment.repository;

import com.skylabng.jaizexpress.payment.PaymentPayload;
import com.skylabng.jaizexpress.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<PaymentPayload> findPaymentById(UUID uuid);

    Optional<PaymentPayload> findPaymentByTransaction(UUID transaction);
}
