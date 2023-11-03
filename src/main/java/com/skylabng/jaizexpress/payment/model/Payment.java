package com.skylabng.jaizexpress.payment.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.PaymentMethod;
import com.skylabng.jaizexpress.enums.PaymentStatus;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "payments")
public class Payment extends AuditableEntity {

    @Basic(optional = false)
    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private UUID transaction;

    @Basic
    @NotNull
    @Column(name = "amount", nullable = false)
    private double amount;

    @Basic(optional = true)
    @Column(name = "ref")
    private String ref;

    @Basic(optional = true)
    @Column(name = "method")
    private PaymentMethod method;

    @Basic(optional = true)
    @Column(name = "message")
    private String message;

    @Basic(optional = true)
    @NotNull
    @Column(name = "url")
    private String url;

    @Basic(optional = true)
    @NotNull
    @Column(name = "status")
    private PaymentStatus status;

    @Basic(optional = true)
    @Column(name = "payment_date")
    private Date paymentDate;
}
