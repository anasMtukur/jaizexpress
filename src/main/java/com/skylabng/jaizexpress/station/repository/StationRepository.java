package com.skylabng.jaizexpress.station.repository;

import com.skylabng.jaizexpress.station.StationPayload;
import com.skylabng.jaizexpress.station.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StationRepository extends JpaRepository<Station, UUID> {
    List<StationPayload> findByZoneId( UUID zoneId );

    Optional<StationPayload> findStationById( UUID id );

    List<StationPayload> findByPosIndexBetween(int start, int end);
}
