package com.skylabng.jaizexpress.payment.service;

import com.skylabng.jaizexpress.klikpay.services.KlikPayService;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.payment.PaymentExternalAPI;
import com.skylabng.jaizexpress.payment.PaymentInternalAPI;
import com.skylabng.jaizexpress.payment.PaymentPayload;
import com.skylabng.jaizexpress.payment.mapper.PaymentMapper;
import com.skylabng.jaizexpress.payment.model.Payment;
import com.skylabng.jaizexpress.payment.repository.PaymentRepository;
import com.skylabng.jaizexpress.transaction.TransactionPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService implements PaymentExternalAPI, PaymentInternalAPI {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    private final KlikPayService klikPayService;

    public PaymentService(PaymentRepository repository, PaymentMapper mapper, KlikPayService klikPayService) {
        this.repository = repository;
        this.mapper = mapper;
        this.klikPayService = klikPayService;
    }

    @Override
    public PagedPayload<PaymentPayload> getPaymentList(Pageable pageable) {
        Page<Payment> page = this.repository.findAll(pageable);
        List<PaymentPayload> list = page.getContent().stream().map(mapper::toPayload).toList();

        return new PagedPayload<PaymentPayload>(list, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public PaymentPayload getTransactionPayment(UUID transactionId) {
        return repository.findPaymentByTransaction( transactionId ).orElseThrow(
                ()->new RuntimeException( "Payment does not exist for this transaction" ));
    }

    @Override
    public PaymentPayload getPaymentByID(UUID id) {
        return repository.findPaymentById( id ).orElseThrow(()->new RuntimeException("Payment with given ID does not exist"));
    }

    @Override
    public PaymentPayload initiatePayment( TransactionPayload transaction ) throws Exception {
        PaymentPayload payment = klikPayService.initiateCheckout( transaction );

        return mapper.toPayload(
                repository.saveAndFlush( mapper.toPayment( payment ) ) );
    }

    @Override
    public PaymentPayload save(PaymentPayload payment) {
        return mapper.toPayload(
                repository.saveAndFlush( mapper.toPayment( payment ) ) );
    }
}
