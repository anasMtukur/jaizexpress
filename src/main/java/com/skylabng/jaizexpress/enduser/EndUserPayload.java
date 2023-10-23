package com.skylabng.jaizexpress.enduser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.UUID;

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
