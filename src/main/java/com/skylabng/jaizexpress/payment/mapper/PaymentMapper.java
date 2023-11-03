package com.skylabng.jaizexpress.payment.mapper;

import com.skylabng.jaizexpress.payment.PaymentPayload;
import com.skylabng.jaizexpress.payment.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface PaymentMapper {
    public Payment toPayment(PaymentPayload payload);
    public PaymentPayload toPayload(Payment payment);
}
