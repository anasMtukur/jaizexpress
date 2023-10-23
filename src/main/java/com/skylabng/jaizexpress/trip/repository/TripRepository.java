package com.skylabng.jaizexpress.trip.repository;

import com.skylabng.jaizexpress.enums.TripStatus;
import com.skylabng.jaizexpress.trip.TripPayload;
import com.skylabng.jaizexpress.trip.model.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
    Page<TripPayload> findByUserId(UUID id, Pageable pageable );

    Page<TripPayload> findByServiceId(UUID id, Pageable pageable);

    List<Trip> findByCardAndStatus( String card, TripStatus status );

    Optional<TripPayload> getOneById( UUID id );
}
