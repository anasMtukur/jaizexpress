package com.skylabng.jaizexpress.utils;

import jakarta.validation.constraints.Null;

public record CSVRecordPayload(
        String index,
        String name,
        String addon,
        String locality,
        String lat,
        String lon,
        @Null
        String error
) {
}
