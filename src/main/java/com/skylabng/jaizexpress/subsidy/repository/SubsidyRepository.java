package com.skylabng.jaizexpress.subsidy.repository;

import com.skylabng.jaizexpress.subsidy.SubsidyPayload;
import com.skylabng.jaizexpress.subsidy.model.Subsidy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubsidyRepository extends JpaRepository<Subsidy, UUID> {
    List<SubsidyPayload> findByServiceId(UUID serviceId );

    Optional<SubsidyPayload> findSubsidyById(UUID id);
}
