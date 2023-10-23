package com.skylabng.jaizexpress.role.repository;

import com.skylabng.jaizexpress.role.RolePayload;
import com.skylabng.jaizexpress.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    List<RolePayload> getRolesByUserId(UUID userId);
}
