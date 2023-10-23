package com.skylabng.jaizexpress.trip.mapper;

import com.skylabng.jaizexpress.trip.TripPayload;
import com.skylabng.jaizexpress.trip.model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TripMapper {
    Trip fromPayloadToTrip( TripPayload payload );

    TripPayload fromTripToPayload( Trip trip );
}
