package com.skylabng.jaizexpress.zone.mapper;

import com.skylabng.jaizexpress.zone.ZonePayload;
import com.skylabng.jaizexpress.zone.model.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface ZoneMapper {
    ZonePayload zoneToPayload( Zone zone );

    Zone payloadToZone( ZonePayload payload );
}
