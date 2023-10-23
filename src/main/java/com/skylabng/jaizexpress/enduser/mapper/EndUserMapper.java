package com.skylabng.jaizexpress.enduser.mapper;

import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enduser.model.EndUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndUserMapper {
    EndUserPayload userToPayload(EndUser endUser);

    EndUser payloadToUser(EndUserPayload endUserPayload);
}
