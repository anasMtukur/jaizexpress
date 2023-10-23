package com.skylabng.jaizexpress.payload;

import java.util.UUID;

public record CompleteCardTripPayload(
        String card,
        UUID destination
) {
}
