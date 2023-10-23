package com.skylabng.jaizexpress.provider;

import com.skylabng.jaizexpress.payload.ProviderDetailsPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProviderExternalAPI {
    PagedPayload<ProviderPayload> getList(Pageable pageable);
    ProviderPayload findById(UUID id);
    ProviderDetailsPayload findByIdWithUsers(UUID id);
    ProviderDetailsPayload findByIdWithUsersAndServices(UUID id);
    ProviderPayload add(ProviderPayload provider);
    void remove(UUID id);
}
