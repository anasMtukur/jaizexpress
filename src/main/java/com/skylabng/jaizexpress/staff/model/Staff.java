package com.skylabng.jaizexpress.staff.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staffs")
public class Staff extends AuditableEntity {
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private UUID user;

    @Basic(optional = false)
    @Column(name = "provider_id", nullable = false)
    private UUID provider;
}
