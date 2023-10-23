package com.skylabng.jaizexpress.payload;

import com.skylabng.jaizexpress.station.StationPayload;
import com.skylabng.jaizexpress.zone.ZonePayload;

import java.util.List;

public record ZoneDetailPayload(
        ZonePayload zone,
        List<StationPayload> stations
) {
}
