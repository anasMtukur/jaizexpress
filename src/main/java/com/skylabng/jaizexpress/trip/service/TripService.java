package com.skylabng.jaizexpress.trip.service;

import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enduser.model.EndUser;
import com.skylabng.jaizexpress.enums.PaymentMethod;
import com.skylabng.jaizexpress.enums.PaymentStatus;
import com.skylabng.jaizexpress.enums.PurchaseMode;
import com.skylabng.jaizexpress.enums.TripStatus;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.station.StationExternalAPI;
import com.skylabng.jaizexpress.station.StationInternalAPI;
import com.skylabng.jaizexpress.station.StationPayload;
import com.skylabng.jaizexpress.station.model.Station;
import com.skylabng.jaizexpress.subsidy.SubsidyInternalAPI;
import com.skylabng.jaizexpress.subsidy.SubsidyPayload;
import com.skylabng.jaizexpress.trip.TripExternalAPI;
import com.skylabng.jaizexpress.trip.TripInternalAPI;
import com.skylabng.jaizexpress.trip.TripPayload;
import com.skylabng.jaizexpress.trip.mapper.TripMapper;
import com.skylabng.jaizexpress.trip.model.Trip;
import com.skylabng.jaizexpress.trip.repository.TripRepository;
import com.skylabng.jaizexpress.zone.ZoneExternalAPI;
import com.skylabng.jaizexpress.zone.ZoneInternalAPI;
import com.skylabng.jaizexpress.zone.ZonePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class TripService implements TripInternalAPI, TripExternalAPI {
    TripRepository repository;
    TripMapper mapper;
    StationInternalAPI stationAPI;
    ZoneInternalAPI zoneAPI;
    SubsidyInternalAPI subsidyAPI;

    public TripService(
            TripRepository repository,
            TripMapper mapper,
            StationInternalAPI stationAPI,
            ZoneInternalAPI zoneAPI,
            SubsidyInternalAPI subsidyAPI
    ){
        this.repository = repository;
        this.mapper = mapper;
        this.stationAPI = stationAPI;
        this.zoneAPI = zoneAPI;
        this.subsidyAPI = subsidyAPI;
    }

    @Override
    public TripPayload initiateTrip(TripPayload payload) throws Exception {
        PurchaseMode mode = payload.getPurchaseMode();
        switch ( mode ){
            case CARD:
                return processCardTrip( payload );

            case APP:
            case AGENT:
                return processTrip( payload );

            default:
                System.out.println("Unsupported Mode");
                break;
        }
        return payload;
    }

    @Override
    public TripPayload completeCardTrip(TripPayload payload) throws Exception {
        return processTrip( payload );
    }

    @Override
    public TripPayload getTripDetails(UUID id) {
        return null;
    }

    @Override
    public TripPayload getTripById(UUID id) {
        return repository.getOneById( id ).orElseThrow(()->new RuntimeException( "Trip with the given id not found" ));
    }

    @Override
    public TripPayload save(TripPayload payload) {
        return mapper.fromTripToPayload(
                repository.saveAndFlush( mapper.fromPayloadToTrip( payload )));
    }

    @Override
    public TripPayload getOngoingCardTrip(String card) throws Exception {
        List<Trip> found = repository.findByCardAndStatus(card, TripStatus.NEW);
        if(found.isEmpty()){
            return null;
            //throw new Exception("Card has not started a trip.");
        }

        List<TripPayload> foundPayload = mapTripListToPayload( found );

        return foundPayload.get( 0 );
    }

    @Override
    public PagedPayload<TripPayload> getTripList(Pageable pageable) {
        Page<Trip> page = this.repository.findAll(pageable);
        List<TripPayload> trips = mapTripListToPayload( page.getContent() );

        return new PagedPayload<TripPayload>(trips, page.getTotalPages(), page.getTotalElements());
    }

    private List<TripPayload> mapTripListToPayload( List<Trip> trips ){
        return trips
                .stream()
                .map(mapper::fromTripToPayload)
                .toList();

    }

    private TripPayload processCardTrip( TripPayload payload ) throws Exception{
        //Check If A Trip Already Exist with this card
        //If origin matches current origin, check date
        //If Old block, see counter
        //If date within +/- 10mins pass / return trip
        //If Trip doesn't exist, check if amount limit is valid on card
        //if limit is invalid throw exception card limit
        // if limit is valid save new and return
        TripPayload trip = getOngoingCardTrip( payload.getCard() );
        if(trip != null){
            boolean isValid = isValidTrip( trip );

            if(!isValid) {
                throw (new Exception("This card is already in a trip. Please complete trip before starting a new one."));
            }

            return trip;
        }
        payload.setPaymentMethod( PaymentMethod.CARD );
        payload.setPaymentStatus( PaymentStatus.WAITING );
        payload.setStatus( TripStatus.NEW );
        //check if there is balance on card
        return mapper.fromTripToPayload(
                repository.saveAndFlush( mapper.fromPayloadToTrip(payload) ));
    }

    private TripPayload processTrip( TripPayload payload ) throws Exception {

        StationPayload origin = stationAPI.getStationById( payload.getOrigin() );
        StationPayload destination = stationAPI.getStationById( payload.getDestination() );

        double amount = calculateTripAmount( origin.posIndex(), destination.posIndex() );

        payload.setAmount( amount );

        payload = applySubsidy( payload );

        return mapper.fromTripToPayload(
                repository.saveAndFlush( mapper.fromPayloadToTrip(payload) ));
    }

    @Override
    public TripPayload applySubsidy( TripPayload payload ){
        if( payload.getSubsidy() == null ){
            return payload;
        }

        SubsidyPayload subsidy = subsidyAPI.getSubsidyById( payload.getSubsidy() );

        double discount = subsidy.discount();
        payload.setDiscount( discount );

        return payload;

    }

    private boolean isValidTrip( TripPayload existingTrip ){
        long MAX_DURATION = MILLISECONDS.convert(20, MINUTES);// 20;

        long duration = (new Date()).getTime() - existingTrip.getCreatedAt().getTime();
        //long diffInMinutes = MILLISECONDS.toMinutes(duration);
        System.out.println( " Since: " + existingTrip.getCreatedAt() );
        System.out.println( duration + " / " + MAX_DURATION );
        return duration < MAX_DURATION;
    }

    private double calculateTripAmount( int originIndex, int destinationIndex ){
        double total = 0;

        List<StationPayload> route = stationAPI.findStationsOnRouteByIndex( originIndex, destinationIndex );
        for (StationPayload station: route) {
            ZonePayload zone = zoneAPI.getZoneById( station.zoneId() );

            total = total + ( zone.baseCharge() + station.addonCharge() );
        }

        return total;
    }
}
