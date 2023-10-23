package com.skylabng.jaizexpress.station;

import java.util.List;
import java.util.UUID;

public interface StationInternalAPI {
    List<StationPayload> getStationsByZone( UUID zoneId );

    StationPayload save( StationPayload station );

    StationPayload getStationById( UUID id );

    List<StationPayload> findStationsOnRouteByIndex( int start, int end );

    List<StationPayload> saveAll( List<StationPayload> station );
}
