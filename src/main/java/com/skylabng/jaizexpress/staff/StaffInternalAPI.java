package com.skylabng.jaizexpress.staff;

import java.util.List;
import java.util.UUID;

public interface StaffInternalAPI {
    List<StaffPayload> findByProvider(UUID id);

    StaffPayload add(StaffPayload payload);

    StaffPayload findStaff(UUID user);
}
