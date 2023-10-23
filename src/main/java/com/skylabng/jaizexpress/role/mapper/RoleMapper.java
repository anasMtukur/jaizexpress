package com.skylabng.jaizexpress.role.mapper;

import com.skylabng.jaizexpress.role.RolePayload;
import com.skylabng.jaizexpress.role.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RolePayload RoleToPayload(Role role);

    Role payloadToRole(RolePayload payload);
}
