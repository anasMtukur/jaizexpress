package com.skylabng.jaizexpress.provider.service;

import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enduser.service.EndUserService;
import com.skylabng.jaizexpress.service.ServiceInternalAPI;
import com.skylabng.jaizexpress.service.ServicePayload;
import com.skylabng.jaizexpress.staff.StaffInternalAPI;
import com.skylabng.jaizexpress.staff.StaffPayload;
import com.skylabng.jaizexpress.provider.ProviderExternalAPI;
import com.skylabng.jaizexpress.provider.ProviderPayload;
import com.skylabng.jaizexpress.provider.mapper.ProviderMapper;
import com.skylabng.jaizexpress.provider.model.Provider;
import com.skylabng.jaizexpress.provider.repository.ProviderRepository;
import com.skylabng.jaizexpress.payload.ProviderDetailsPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.context.ApplicationEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProviderService implements ProviderExternalAPI {
    private static final Logger LOG = LoggerFactory.getLogger(EndUserService.class);
    private final ProviderRepository repository;
    private final ProviderMapper mapper;
    private final ApplicationEventPublisher events;
    private StaffInternalAPI staffApi;
    private EndUserInternalAPI userAPI;
    private ServiceInternalAPI serviceApi;

    public ProviderService(ApplicationEventPublisher events,
                           ProviderRepository repository,
                           StaffInternalAPI staffApi,
                           EndUserInternalAPI userAPI,
                           ServiceInternalAPI serviceApi,
                           ProviderMapper mapper) {
        this.events = events;
        this.repository = repository;
        this.mapper = mapper;
        this.staffApi = staffApi;
        this.userAPI = userAPI;
        this.serviceApi = serviceApi;
    }


    @Override
    public PagedPayload<ProviderPayload> getList(Pageable pageable) {
        Page<Provider> page = this.repository.findAll(pageable);
        List<ProviderPayload> orgs = page.getContent()
                .stream()
                .map(mapper::organizationToPayload)
                .toList();

        return new PagedPayload<ProviderPayload>(orgs, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public ProviderPayload findById(UUID id) {
        return repository.findOrgById( id );
    }

    @Override
    public ProviderDetailsPayload findByIdWithUsers(UUID id) {

        ProviderPayload entity = repository.findOrgById( id );
        List<StaffPayload> staffs = staffApi.findByProvider( id );
        List<EndUserPayload> users = new ArrayList<>();
        for( StaffPayload staff: staffs ){
            users.add( userAPI.getUserById( staff.user() ) );
        }

        return new ProviderDetailsPayload( entity, users, null );
    }

    @Override
    public ProviderDetailsPayload findByIdWithUsersAndServices(UUID id) {
        ProviderPayload entity = repository.findOrgById( id );
        List<StaffPayload> staffs = staffApi.findByProvider( id );
        List<EndUserPayload> users = new ArrayList<>();
        for( StaffPayload staff: staffs ){
            users.add( userAPI.getUserById( staff.user() ) );
        }
        List<ServicePayload> services = serviceApi.getProviderServices( id );

        return new ProviderDetailsPayload( entity, users, services );
    }

    @Override
    @Transactional
    public ProviderPayload add(ProviderPayload organization) {
        ProviderPayload dto = mapper.organizationToPayload(
                repository.save(mapper.payloadToOrganization(organization))
        );
        return dto;
    }

    @Override
    public void remove(UUID id) {
        repository.deleteById(id);
    }
}
