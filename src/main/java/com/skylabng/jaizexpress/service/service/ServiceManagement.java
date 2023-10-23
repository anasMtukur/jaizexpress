package com.skylabng.jaizexpress.service.service;

import com.skylabng.jaizexpress.enums.NigerianState;
import com.skylabng.jaizexpress.enums.ServiceStatus;
import com.skylabng.jaizexpress.payload.ServiceDetailsPayload;
import com.skylabng.jaizexpress.service.ServiceExternalAPI;
import com.skylabng.jaizexpress.service.ServiceInternalAPI;
import com.skylabng.jaizexpress.service.ServicePayload;
import com.skylabng.jaizexpress.service.mapper.ServiceMapper;
import com.skylabng.jaizexpress.service.repository.ServiceRepository;
import com.skylabng.jaizexpress.subsidy.SubsidyInternalAPI;
import com.skylabng.jaizexpress.subsidy.SubsidyPayload;
import com.skylabng.jaizexpress.zone.ZoneInternalAPI;
import com.skylabng.jaizexpress.zone.ZonePayload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceManagement implements ServiceInternalAPI, ServiceExternalAPI {
    ServiceMapper mapper;
    ServiceRepository repository;
    ZoneInternalAPI zoneAPI;
    SubsidyInternalAPI subsidyAPI;

    public ServiceManagement( ServiceMapper mapper, ServiceRepository repository, ZoneInternalAPI zoneAPI, SubsidyInternalAPI subsidyAPI ){
        this.mapper = mapper;
        this.repository = repository;
        this.zoneAPI = zoneAPI;
        this.subsidyAPI = subsidyAPI;
    }

    @Override
    public ServiceDetailsPayload getServiceDetails(UUID id) {
        ServicePayload payload = repository.findServiceById( id );
        List<ZonePayload> zones = zoneAPI.getZonesByServiceId( id );
        List<SubsidyPayload> subsidies = subsidyAPI.getSubsidiesByServiceId( id );

        return new ServiceDetailsPayload( payload, zones, subsidies );
    }

    @Override
    public List<ServicePayload> getStateServices(NigerianState state) {
        return repository.findByState( state );
    }

    @Override
    public ServicePayload addService(ServicePayload payload) {
        return mapper.serviceToPayload(
                repository.save(mapper.payloadToService(payload))
        );
    }

    @Override
    public ServicePayload updateServiceStatus(UUID serviceId, ServiceStatus status) {
        Optional<com.skylabng.jaizexpress.service.model.Service> optionalService = repository.findById( serviceId );

        com.skylabng.jaizexpress.service.model.Service service = optionalService.orElseThrow();
        service.setStatus( status );

        return mapper.serviceToPayload( repository.save( service ) );
    }

    @Override
    public void remove(UUID id) {
        repository.deleteById( id );
    }

    @Override
    public List<ServicePayload> getProviderServices(UUID provider) {
        return repository.findByProvider( provider );
    }
}
