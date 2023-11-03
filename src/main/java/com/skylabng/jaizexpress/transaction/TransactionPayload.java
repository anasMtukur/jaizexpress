package com.skylabng.jaizexpress.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skylabng.jaizexpress.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionPayload {
    private UUID id;
    private UUID userId;
    private TransactionTarget target;
    private UUID card;
    private UUID wallet;
    private double amount;
    private TransactionType type;
    private boolean isBalanced;
    private TransactionStatus status;
    private Date createdAt;
    private Date updatedAt;
}
