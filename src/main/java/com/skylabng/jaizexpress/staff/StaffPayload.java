package com.skylabng.jaizexpress.staff;

import java.util.Date;
import java.util.UUID;

public record StaffPayload(
        UUID id,
        UUID user,
        UUID provider,
        Date createdAt,
        Date UpdatedAt
) {
}
