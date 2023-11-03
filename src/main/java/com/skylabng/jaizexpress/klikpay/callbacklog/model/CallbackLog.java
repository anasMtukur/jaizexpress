package com.skylabng.jaizexpress.klikpay.callbacklog.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "callback_logs")
public class CallbackLog extends AuditableEntity {
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private String status;

    @Basic(optional = true)
    @NotNull
    @Column(name = "amount")
    private String amount;

    @Basic(optional = true)
    @NotNull
    @Column(name = "currency")
    private String currency;

    @Basic(optional = true)
    @NotNull
    @Column(name = "trace_id")
    private String traceId;

    @Basic(optional = true)
    @NotNull
    @Column(name = "reference_id")
    private String referenceId;
}
