package com.skylabng.jaizexpress.role.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.RoleName;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
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
@Table(name = "user_roles")
public class Role extends AuditableEntity {
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private RoleName roleName;
}
