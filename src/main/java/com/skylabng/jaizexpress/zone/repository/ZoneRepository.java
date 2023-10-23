package com.skylabng.jaizexpress.zone.repository;

import com.skylabng.jaizexpress.zone.ZonePayload;
import com.skylabng.jaizexpress.zone.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, UUID> {
    List<ZonePayload> findByServiceId( UUID serviceId );

    Optional<ZonePayload> findZoneById(UUID id);
}
