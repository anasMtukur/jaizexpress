package com.skylabng.jaizexpress.enduser.model;
import com.skylabng.jaizexpress.audit.AuditableEntity;
import lombok.*;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
@Table(name = "end_users")
public class EndUser extends AuditableEntity{
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Basic(optional = false)
    @Column(name = "username", nullable = false)
    private String username;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "mobile")
    private String mobile;

    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "reset_token")
    private String resetToken;

    @Basic
    @Column(name = "is_deleted", columnDefinition = "boolean DEFAULT false NOT NULL", nullable = false)
    private boolean isDeleted;

    @Basic
    @Column(name = "block_code")
    private String blockCode;

    @Basic
    @Column(name = "is_blocked", columnDefinition = "boolean DEFAULT false NOT NULL", nullable = false)
    private boolean isBlocked;
}
