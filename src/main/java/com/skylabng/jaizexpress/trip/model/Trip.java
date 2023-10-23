package com.skylabng.jaizexpress.trip.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.PaymentMethod;
import com.skylabng.jaizexpress.enums.PaymentStatus;
import com.skylabng.jaizexpress.enums.PurchaseMode;
import com.skylabng.jaizexpress.enums.TripStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author Anas M. Tukur <anasmtukur91@gmail.com>
 */
@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trips")
public class Trip extends AuditableEntity {

    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Basic
    @Column(name = "card", nullable = true)
    private String card;

    @Basic(optional = false)
    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Basic(optional = false)
    @Column(name = "origin", nullable = false)
    private UUID origin;

    @Basic
    @Column(name = "destination")
    private UUID destination;

    @Basic
    @Column(name = "amount")
    private double amount;

    @Basic
    @Column(name = "discount")
    private double discount;

    @Basic
    @Column(name = "subsidy")
    private UUID subsidy;

    @Basic
    @Column(name = "qrUrl")
    private String qrUrl;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_mode", nullable = false)
    private PurchaseMode purchaseMode;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TripStatus status;
}
