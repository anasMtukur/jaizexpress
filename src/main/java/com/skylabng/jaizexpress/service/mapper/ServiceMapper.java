package com.skylabng.jaizexpress.service.mapper;

import com.skylabng.jaizexpress.service.ServicePayload;
import com.skylabng.jaizexpress.service.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface ServiceMapper {
    ServicePayload serviceToPayload(Service service);

    Service payloadToService(ServicePayload payload);
}
