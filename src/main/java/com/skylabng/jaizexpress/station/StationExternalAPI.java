package com.skylabng.jaizexpress.station;

import java.util.List;

public interface StationExternalAPI {

    StationPayload save( StationPayload station );

    List<StationPayload> saveAll(List<StationPayload> station );
}
