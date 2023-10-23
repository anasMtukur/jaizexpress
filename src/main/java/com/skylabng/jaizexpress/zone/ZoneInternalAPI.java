package com.skylabng.jaizexpress.zone;

import java.util.List;
import java.util.UUID;

public interface ZoneInternalAPI {
    List<ZonePayload> getZonesByServiceId( UUID serviceId );

    ZonePayload save( ZonePayload zone );

    List<ZonePayload> saveAll( List<ZonePayload> zones );

    ZonePayload getZoneById( UUID id );
}
