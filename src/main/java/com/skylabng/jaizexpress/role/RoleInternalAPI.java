package com.skylabng.jaizexpress.role;

import java.util.List;
import java.util.UUID;

public interface RoleInternalAPI {
    List<RolePayload> findByUserId(UUID userId);

    RolePayload addRole(RolePayload payload);
}
