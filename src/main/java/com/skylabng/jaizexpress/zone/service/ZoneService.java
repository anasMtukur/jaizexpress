package com.skylabng.jaizexpress.zone.service;

import com.skylabng.jaizexpress.payload.ZoneDetailPayload;
import com.skylabng.jaizexpress.station.StationInternalAPI;
import com.skylabng.jaizexpress.station.StationPayload;
import com.skylabng.jaizexpress.zone.ZoneExternalAPI;
import com.skylabng.jaizexpress.zone.ZoneInternalAPI;
import com.skylabng.jaizexpress.zone.ZonePayload;
import com.skylabng.jaizexpress.zone.mapper.ZoneMapper;
import com.skylabng.jaizexpress.zone.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ZoneService implements ZoneInternalAPI, ZoneExternalAPI {
    private final ZoneRepository repository;
    private final ZoneMapper mapper;

    private final StationInternalAPI stationAPI;

    public ZoneService(ZoneRepository repository, ZoneMapper mapper, StationInternalAPI stationAPI){
        this.repository = repository;
        this.mapper = mapper;
        this.stationAPI = stationAPI;
    }

    @Override
    public List<ZonePayload> getZonesByServiceId(UUID serviceId) {
        return repository.findByServiceId( serviceId );
    }

    @Override
    public ZoneDetailPayload getZoneDetails(UUID id) throws Exception {
        Optional<ZonePayload> optZone = repository.findZoneById( id );
        ZonePayload zone = optZone.orElseThrow( () -> new Exception("Zone not found") );

        List<StationPayload> stations = stationAPI.getStationsByZone( id );

        return new ZoneDetailPayload( zone, stations );
    }

    @Override
    public ZonePayload save(ZonePayload zone) {
        return mapper.zoneToPayload(
                repository.saveAndFlush( mapper.payloadToZone( zone ) ) );
    }

    @Override
    public List<ZonePayload> saveAll(List<ZonePayload> zones) {
        List<ZonePayload> saved = new ArrayList<>();

        for (ZonePayload zone: zones) {
            saved.add(
                    mapper.zoneToPayload(
                            repository.saveAndFlush( mapper.payloadToZone( zone ) ) )
            );
        }

        return saved;
    }

    @Override
    public ZonePayload getZoneById(UUID id) {
        return repository.findZoneById( id ).orElseThrow(()->new RuntimeException("Zone with given id not found"));
    }
}
