package com.skylabng.jaizexpress.role.service;

import com.skylabng.jaizexpress.role.RoleInternalAPI;
import com.skylabng.jaizexpress.role.RolePayload;
import com.skylabng.jaizexpress.role.mapper.RoleMapper;
import com.skylabng.jaizexpress.role.model.Role;
import com.skylabng.jaizexpress.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService implements RoleInternalAPI {
    private final RoleRepository repository;

    private final RoleMapper mapper;

    public RoleService(RoleRepository repository, RoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<RolePayload> findByUserId(UUID userId) {
        return repository.getRolesByUserId(userId);
    }

    @Override
    public RolePayload addRole(RolePayload payload) {
        Role entity = mapper.payloadToRole(payload);

        return mapper.RoleToPayload(repository.saveAndFlush(entity));
    }
}
