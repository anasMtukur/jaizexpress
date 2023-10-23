package com.skylabng.jaizexpress.service.repository;

import com.skylabng.jaizexpress.enums.NigerianState;
import com.skylabng.jaizexpress.provider.ProviderPayload;
import com.skylabng.jaizexpress.service.ServicePayload;
import com.skylabng.jaizexpress.service.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
    List<ServicePayload> findByState( NigerianState state );
    List<ServicePayload> findByProvider( UUID provider );
    ServicePayload findServiceById(UUID id );
}
