package com.skylabng.jaizexpress.enduser.service;

import com.skylabng.jaizexpress.enduser.EndUserExternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enduser.mapper.EndUserMapper;
import com.skylabng.jaizexpress.enduser.model.EndUser;
import com.skylabng.jaizexpress.enduser.repository.EndUserRepository;
import com.skylabng.jaizexpress.payload.PagedPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EndUserService implements EndUserExternalAPI, EndUserInternalAPI {
    private static final Logger LOG = LoggerFactory.getLogger(EndUserService.class);

    private final EndUserRepository repository;
    private final EndUserMapper mapper;

    public EndUserService(EndUserRepository repository, EndUserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PagedPayload<EndUserPayload> getUserList(Pageable pageable) {
        Page<EndUser> page = this.repository.findAll(pageable);
        List<EndUserPayload> users = page.getContent()
                .stream()
                .map(mapper::userToPayload)
                .toList();

        return new PagedPayload<EndUserPayload>(users, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public boolean checkUserExist(String username, String email, String mobile) {
        return repository.existsByUsername( username )
                || repository.existsByEmail( email )
                || repository.existsByMobile( mobile );
    }

    @Override
    public EndUserPayload getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public EndUserPayload getUserByEmailOrMobile(String email, String mobile) {
        return repository.findByEmailOrMobile(email, mobile);
    }

    @Override
    public EndUserPayload getUserByResetCode(String resetCode) {

        return repository.findByResetToken(resetCode);
    }

    @Override
    public EndUserPayload initiateForgotPassword(String username) {
        EndUserPayload endUserPayload = this.getUserByUsername( username );
        EndUser entity = mapper.payloadToUser(endUserPayload);

        String resetCode = String.valueOf(UUID.randomUUID());

        entity.setResetToken( resetCode );

        return mapper.userToPayload(
                repository.saveAndFlush(entity));
    }

    @Override
    public EndUserPayload resetPassword(String resetCode, String newPassword) {
        EndUserPayload endUserPayload = this.getUserByResetCode(resetCode);
        EndUser entity = mapper.payloadToUser(endUserPayload);

        entity.setPassword( newPassword );
        entity.setResetToken( null );

        return mapper.userToPayload(
                repository.saveAndFlush(entity));
    }

    @Override
    public EndUserPayload getUserById(UUID id) {
        return repository.findUserById( id );
    }

    @Override
    @Transactional
    public EndUserPayload save(EndUserPayload endUserPayload) {
        EndUser entity = mapper.payloadToUser(endUserPayload);
        return mapper.userToPayload(
                repository.saveAndFlush(entity));
    }


}
