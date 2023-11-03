package com.skylabng.jaizexpress.wallet.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.WalletStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "wallets")
public class Wallet extends AuditableEntity {
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "balance")
    private double balance;

    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WalletStatus status;
}
