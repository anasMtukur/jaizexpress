package com.skylabng.jaizexpress.service;

import com.skylabng.jaizexpress.enums.NigerianState;
import com.skylabng.jaizexpress.enums.ServiceStatus;
import com.skylabng.jaizexpress.payload.ServiceDetailsPayload;

import java.util.List;
import java.util.UUID;

public interface ServiceExternalAPI {
    ServiceDetailsPayload getServiceDetails( UUID id );
    List<ServicePayload> getStateServices(NigerianState state);
    ServicePayload addService( ServicePayload payload );
    ServicePayload updateServiceStatus(UUID serviceId, ServiceStatus status );
    void remove(UUID id);
}
