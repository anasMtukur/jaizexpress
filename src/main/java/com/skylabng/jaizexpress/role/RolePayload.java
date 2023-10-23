package com.skylabng.jaizexpress.role;

import com.skylabng.jaizexpress.enums.RoleName;

import java.util.UUID;

public record RolePayload (
        UUID id,
        UUID userId,
        RoleName roleName
){
}
