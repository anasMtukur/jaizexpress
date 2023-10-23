package com.skylabng.jaizexpress.trip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.skylabng.jaizexpress.enums.PaymentMethod;
import com.skylabng.jaizexpress.enums.PaymentStatus;
import com.skylabng.jaizexpress.enums.PurchaseMode;
import com.skylabng.jaizexpress.enums.TripStatus;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripPayload {
        @Null
        private UUID id;
        @Null
        private UUID userId;
        private UUID serviceId;
        @Null
        private String card;
        private UUID origin;
        @Null
        private UUID destination;
        @Null
        private double amount;
        @Null
        private double discount;
        @Null
        private UUID subsidy;
        @Null
        private String qrUrl;
        private PurchaseMode purchaseMode;
        private PaymentMethod paymentMethod;
        @Null
        private PaymentStatus paymentStatus;
        private TripStatus status;
        @Null
        Date createdAt;
        @Null
        Date updatedAt;
}
