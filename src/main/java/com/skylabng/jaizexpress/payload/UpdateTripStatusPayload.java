package com.skylabng.jaizexpress.payload;

import com.skylabng.jaizexpress.enums.TripStatus;

public record UpdateTripStatusPayload(
        TripStatus newStatus
) {
}
