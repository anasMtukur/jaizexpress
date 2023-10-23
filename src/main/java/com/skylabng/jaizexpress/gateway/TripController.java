package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enums.TripStatus;
import com.skylabng.jaizexpress.payload.*;
import com.skylabng.jaizexpress.trip.TripExternalAPI;
import com.skylabng.jaizexpress.trip.TripPayload;
import com.skylabng.jaizexpress.utils.PagingUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@Tag(name = "Trip API", description = "API for initiating, completing and managing trips")
@RequestMapping("/api/trips")
public class TripController {
    private final TripExternalAPI api;
    private final EndUserInternalAPI userAPI;

    public TripController(TripExternalAPI api, EndUserInternalAPI userAPI) {
        this.api = api;
        this.userAPI = userAPI;
    }

    @Operation(
            summary = "List All Trips",
            description = "Returns a paged list of all the trips recorded. You can call this endpoint with page parameter to move between pages.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedPayload<TripPayload>> find(
            Principal principal,
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "search", required = false, defaultValue = StringUtils.EMPTY) String searchCriteria,
            @RequestParam(name = "searchtext", required = false, defaultValue = "") String searchText,
            @RequestParam(name = "pagesize", required = false, defaultValue = "1000") int pageSize,
            @RequestParam(name = "sortby", required = false, defaultValue = "date") String sortBy,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(name = "createdBy", required = false, defaultValue = "") String createdBy) throws Exception {

        PageRequest pageRequest = PagingUtil.getPageRequestObject(pageNumber, pageSize, sortBy, sortDirection);
        PagedPayload<TripPayload> response = api.getTripList(pageRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Initiate Trip",
            description = "Call this API to book/buy ticket for a new trip. When both origin and destination are present and purchase mode is AGENT," +
                    "then the full trip will be created. If only origin is available, it means that the trip is initiated with a Card amd will be " +
                    "completed at the destination.")
    @PostMapping( value = "/initiate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<TripPayload> initiateTrip(Principal principal, @RequestBody TripPayload payload ) throws Exception {
        EndUserPayload user = userAPI.getUserByUsername( principal.getName() );

        payload.setUserId( user.id() );
        payload.setStatus( TripStatus.NEW );
        TripPayload entity = api.initiateTrip( payload );

        return ResponseEntity.ok( entity );
    }


    @Operation(
            summary = "Complete a trip",
            description = "Complete the trip initiated by the card on entry at the origin. This endpoint is only applicable " +
                    "to trips initiate using a card on entry of a bus or train station")
    @PostMapping( value = "/complete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<TripPayload> completeCardTrip(
            Principal principal,
            @RequestBody CompleteCardTripPayload payload ) throws Exception {
        TripPayload entity = api.getOngoingCardTrip( payload.card() );
        entity.setCard( payload.card() );
        entity.setDestination( payload.destination() );

        entity = api.completeCardTrip( entity );
        return ResponseEntity.ok( entity );
    }

    @Operation(
            summary = "Apply Subsidy to Trip",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Trip ID") },
            description = "Apply subsidy to a trip ticket that is already created.")
    @PutMapping( value = "/{id}/apply_subsidy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<TripPayload> applySubsidy(
            Principal principal,
            @PathVariable( name = "id" ) String id,
            @RequestBody ApplySubsidyPayload payload ) throws Exception {
        TripPayload entity = api.getTripById( UUID.fromString( id ));
        entity.setSubsidy( payload.subsidy() );

        entity = api.applySubsidy( entity );
        return ResponseEntity.ok( entity );
    }

    @Operation(
            summary = "Update Trip Payment Status",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Trip ID") },
            description = "Update payment status and method for a trip after successful payment is received.")
    @PutMapping( value = "/{id}/update_payment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<TripPayload> updatePayment(
            Principal principal,
            @PathVariable( name = "id" ) String id,
            @RequestBody TripPaymentPayload payload ) throws Exception {
        TripPayload entity = api.getTripById( UUID.fromString( id ));
        entity.setPaymentMethod( payload.paymentMethod() );
        entity.setPaymentStatus( payload.paymentStatus() );

        entity = api.save( entity );
        return ResponseEntity.ok( entity );
    }

    @Operation(
            summary = "Update Trip Status",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Trip ID") },
            description = "Update payment status from NEW to VALID, IN_TRIP, COMPLETED or CANCELLED to indicate different stages of the trip")
    @PutMapping( value = "/{id}/update_status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<TripPayload> updateStatus(
            Principal principal,
            @PathVariable( name = "id" ) String id,
            @RequestBody UpdateTripStatusPayload payload ) throws Exception {
        TripPayload entity = api.getTripById( UUID.fromString( id ));
        entity.setStatus( payload.newStatus() );

        entity = api.save( entity );
        return ResponseEntity.ok( entity );
    }

    @Operation(
            summary = "Get Trip Details",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Trip ID") },
            description = "Returns full details of a trip.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TripPayload> getTripDetails(Principal principal, @PathVariable(name = "id") String id) throws Exception {
        TripPayload entity = api.getTripDetails(UUID.fromString(id));
        return ResponseEntity.ok( entity );
    }
}
