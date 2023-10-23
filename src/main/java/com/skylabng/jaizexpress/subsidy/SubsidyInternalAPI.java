package com.skylabng.jaizexpress.subsidy;

import com.skylabng.jaizexpress.zone.ZonePayload;

import java.util.List;
import java.util.UUID;

public interface SubsidyInternalAPI {
    List<SubsidyPayload> getSubsidiesByServiceId(UUID serviceId );

    SubsidyPayload getSubsidyById( UUID id );

    SubsidyPayload save( SubsidyPayload zone );
}
