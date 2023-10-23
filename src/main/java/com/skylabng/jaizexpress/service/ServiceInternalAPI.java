package com.skylabng.jaizexpress.service;

import com.skylabng.jaizexpress.enums.NigerianState;
import com.skylabng.jaizexpress.enums.ServiceStatus;

import java.util.List;
import java.util.UUID;

public interface ServiceInternalAPI {
    List<ServicePayload> getProviderServices(UUID provider);
}
