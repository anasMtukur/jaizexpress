package com.skylabng.jaizexpress.provider.repository;

import com.skylabng.jaizexpress.provider.ProviderPayload;
import com.skylabng.jaizexpress.provider.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
    ProviderPayload findOrgById(UUID id);
}
