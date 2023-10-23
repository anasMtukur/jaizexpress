package com.skylabng.jaizexpress.station;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.skylabng.jaizexpress.enums.StationStatus;
import jakarta.validation.constraints.Null;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StationPayload(
        UUID id,
        UUID zoneId,
        String name,
        double addonCharge,
        StationStatus status,
        String locality,
        int posIndex,
        @Null
        double lat,
        @Null
        double lon,
        @JsonIgnore
        @Null
        String geoHash

) {
}
