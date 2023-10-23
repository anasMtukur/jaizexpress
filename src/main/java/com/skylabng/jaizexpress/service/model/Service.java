package com.skylabng.jaizexpress.service.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.NigerianState;
import com.skylabng.jaizexpress.enums.ServiceStatus;
import com.skylabng.jaizexpress.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "services")
public class Service  extends AuditableEntity {

    @Basic(optional = false)
    @Column(name = "provider_id", nullable = false)
    private UUID provider;

    @Basic(optional = false)
    @Column(name = "logo", nullable = false)
    private String logo;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private ServiceType serviceType;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic(optional = false)
    @Column(name = "description", nullable = false)
    private String description;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServiceStatus status;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private NigerianState state;
}
