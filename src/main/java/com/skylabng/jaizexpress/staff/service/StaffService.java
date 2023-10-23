package com.skylabng.jaizexpress.staff.service;

import com.skylabng.jaizexpress.staff.StaffInternalAPI;
import com.skylabng.jaizexpress.staff.StaffPayload;
import com.skylabng.jaizexpress.staff.mapper.StaffMapper;
import com.skylabng.jaizexpress.staff.model.Staff;
import com.skylabng.jaizexpress.staff.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StaffService implements StaffInternalAPI {
    private final StaffRepository repository;

    private final StaffMapper mapper;

    public StaffService(StaffRepository repository, StaffMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    public List<StaffPayload> findByProvider(UUID id) {

        return repository.findByProvider( id );
    }

    @Override
    public StaffPayload add(StaffPayload payload) {

        Staff entity = mapper.payloadToStaff( payload );

        return mapper.StaffToPayload( repository.saveAndFlush(entity) );
    }

    @Override
    public StaffPayload findStaff(UUID user) {
        return repository.getOneByUser( user );
    }
}
