package com.skylabng.jaizexpress.enduser.repository;

import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enduser.model.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EndUserRepository extends JpaRepository<EndUser, UUID> {
    public EndUserPayload findByUsername(String username);

    public EndUserPayload findByEmail(String email);

    public EndUserPayload findByMobile(String mobile);

    public EndUserPayload findByResetToken(String resetToken);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    public boolean existsByMobile(String mobile);

    public EndUserPayload findByEmailAndResetToken(String email, String token);

    public EndUserPayload findByEmailOrMobile(String email, String mobile);

    public EndUserPayload findUserById( UUID id );
}
