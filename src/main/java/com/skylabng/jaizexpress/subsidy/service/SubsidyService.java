package com.skylabng.jaizexpress.subsidy.service;

import com.skylabng.jaizexpress.subsidy.SubsidyExternalAPI;
import com.skylabng.jaizexpress.subsidy.SubsidyInternalAPI;
import com.skylabng.jaizexpress.subsidy.SubsidyPayload;
import com.skylabng.jaizexpress.subsidy.mapper.SubsidyMapper;
import com.skylabng.jaizexpress.subsidy.repository.SubsidyRepository;
import com.skylabng.jaizexpress.zone.ZonePayload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubsidyService implements SubsidyInternalAPI, SubsidyExternalAPI {
    private final SubsidyRepository repository;
    private final  SubsidyMapper mapper;

    public SubsidyService(SubsidyRepository repository, SubsidyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public List<SubsidyPayload> getSubsidiesByServiceId(UUID serviceId) {
        return repository.findByServiceId( serviceId );
    }

    @Override
    public SubsidyPayload getSubsidyById(UUID id) {
        return repository.findSubsidyById( id ).orElseThrow(()->new RuntimeException("Subsidy with given ID does not exist"));
    }

    @Override
    public SubsidyPayload save(SubsidyPayload payload) {
        return mapper.subsidyToPayload(
                repository.save( mapper.payloadToSubsidy( payload )));
    }
}
