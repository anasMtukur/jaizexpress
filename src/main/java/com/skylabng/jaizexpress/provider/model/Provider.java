package com.skylabng.jaizexpress.provider.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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
@Table(name = "providers")
public class Provider extends AuditableEntity {
    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic(optional = false)
    @Column(name = "logo", nullable = false)
    private String logo;

    @Basic(optional = false)
    @Column(name = "email", nullable = false)
    private String email;

    @Basic(optional = false)
    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Basic(optional = false)
    @Column(name = "url", nullable = false)
    private String url;
}
