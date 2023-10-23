package com.skylabng.jaizexpress.zone;

import com.skylabng.jaizexpress.payload.ZoneDetailPayload;

import java.util.UUID;

public interface ZoneExternalAPI {
    ZoneDetailPayload getZoneDetails( UUID id ) throws Exception;

    ZonePayload save( ZonePayload zone );
}
