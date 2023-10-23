package com.skylabng.jaizexpress.station.service;

import com.skylabng.jaizexpress.station.StationExternalAPI;
import com.skylabng.jaizexpress.station.StationInternalAPI;
import com.skylabng.jaizexpress.station.StationPayload;
import com.skylabng.jaizexpress.station.mapper.StationMapper;
import com.skylabng.jaizexpress.station.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StationService implements StationInternalAPI, StationExternalAPI {
    private final StationRepository repository;

    private final StationMapper mapper;

    public StationService(StationRepository repository, StationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<StationPayload> getStationsByZone(UUID zoneId) {
        return repository.findByZoneId( zoneId );
    }

    @Override
    public StationPayload save(StationPayload station) {
        return mapper.stationToPayload(
                repository.save( mapper.payloadToStation( station ) ) );
    }

    @Override
    public StationPayload getStationById(UUID id) {

        return repository.findStationById( id ).orElseThrow(()->new RuntimeException("Station with ID not found"));
    }

    @Override
    public List<StationPayload> findStationsOnRouteByIndex(int start, int end) {
        return repository.findByPosIndexBetween(start, end);
    }

    @Override
    public List<StationPayload> saveAll(List<StationPayload> stations) {
        List<StationPayload> saved = new ArrayList<>();

        for (StationPayload station: stations ) {
            saved.add(mapper.stationToPayload(
                    repository.save( mapper.payloadToStation( station ) ) ) );
        }

        return saved;
    }
}
