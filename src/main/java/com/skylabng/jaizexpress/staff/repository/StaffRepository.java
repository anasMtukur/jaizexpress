package com.skylabng.jaizexpress.staff.repository;

import com.skylabng.jaizexpress.staff.StaffPayload;
import com.skylabng.jaizexpress.staff.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    StaffPayload getOneByUser(UUID user);

    List<StaffPayload> findByProvider(UUID providerId);
}
