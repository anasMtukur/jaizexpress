package com.skylabng.jaizexpress.provider.mapper;

import com.skylabng.jaizexpress.provider.ProviderPayload;
import com.skylabng.jaizexpress.provider.model.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProviderMapper {
    ProviderPayload organizationToPayload(Provider entity);

    Provider payloadToOrganization(ProviderPayload payload);
}
