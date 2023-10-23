package com.skylabng.jaizexpress.payload;

import com.skylabng.jaizexpress.service.ServicePayload;
import com.skylabng.jaizexpress.subsidy.SubsidyPayload;
import com.skylabng.jaizexpress.zone.ZonePayload;

import java.util.List;

public record ServiceDetailsPayload(
        ServicePayload service,
        List<ZonePayload> zones,
        List<SubsidyPayload> subsidies
) {
}
