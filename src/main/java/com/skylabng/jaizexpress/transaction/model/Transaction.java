package com.skylabng.jaizexpress.transaction.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
@Table(name = "transactions")
public class Transaction extends AuditableEntity {
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_target")
    private TransactionTarget target;

    @Basic
    @Column(name = "card")
    private UUID card;

    @Basic
    @Column(name = "wallet")
    private UUID wallet;

    @Basic
    @NotNull
    @Column(name = "amount", nullable = false)
    private double amount;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Basic(optional = false)
    @NotNull
    @Column(name = "is_balanced", nullable = false)
    private boolean isBalanced;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;
}
