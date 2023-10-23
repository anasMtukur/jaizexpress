package com.skylabng.jaizexpress.subsidy.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.ServiceStatus;
import com.skylabng.jaizexpress.enums.SubsidyStatus;
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
@Table(name = "subsidies")
public class Subsidy extends AuditableEntity {
    @Basic(optional = false)
    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic(optional = false)
    @Column(name = "description", nullable = false)
    private String description;

    @Basic(optional = false)
    @Column(name = "discount", nullable = false)
    private double discount;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubsidyStatus status;
}
