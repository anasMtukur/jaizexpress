package com.skylabng.jaizexpress.trip;

import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TripExternalAPI {
    TripPayload initiateTrip( TripPayload payload ) throws Exception;

    TripPayload completeCardTrip( TripPayload payload ) throws Exception;

    TripPayload applySubsidy( TripPayload payload ) throws Exception;

    TripPayload getTripDetails( UUID id );

    TripPayload getTripById( UUID id );

    TripPayload save( TripPayload payload );

    TripPayload getOngoingCardTrip(String card) throws Exception;

    PagedPayload<TripPayload> getTripList(Pageable pageable);
}
