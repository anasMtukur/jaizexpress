package com.skylabng.jaizexpress.card.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.CardStatus;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "cards")
public class Card  extends AuditableEntity {
    @Basic(optional = true)
    @Column(name = "user_id", nullable = true)
    private UUID userId;

    @Basic
    @Column(name = "number", nullable = false)
    private String number;

    @Basic
    @Column(name = "balance", nullable = false)
    private double balance;

    @Basic
    @Column(name = "status", nullable = false)
    private CardStatus status;
}
