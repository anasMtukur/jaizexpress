package com.skylabng.jaizexpress.zone.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import jakarta.persistence.*;
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
@Table(name = "zones")
public class Zone  extends AuditableEntity {
    @Basic(optional = false)
    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "base_charge", nullable = false)
    private double baseCharge;

}