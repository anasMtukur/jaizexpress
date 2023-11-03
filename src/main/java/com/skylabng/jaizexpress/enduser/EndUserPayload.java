package com.skylabng.jaizexpress.enduser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EndUserPayload(
        UUID id,
        String firstName,
        String lastName,
        String username,
        String email,
        String mobile,
        @JsonIgnore
        String password,
        String resetToken,
        boolean isDeleted,
        boolean isBlocked,
        String blockCode,
        Date createdAt,
        Date updatedAt
) {
}
