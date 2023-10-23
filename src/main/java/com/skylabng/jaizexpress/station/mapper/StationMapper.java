package com.skylabng.jaizexpress.station.mapper;

import com.skylabng.jaizexpress.station.StationPayload;
import com.skylabng.jaizexpress.station.model.Station;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StationMapper {
    StationPayload stationToPayload( Station station );

    Station payloadToStation( StationPayload payload );
}
