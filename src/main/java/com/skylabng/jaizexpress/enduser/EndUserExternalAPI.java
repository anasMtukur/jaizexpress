package com.skylabng.jaizexpress.enduser;

import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.payload.UserDetailsPayload;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EndUserExternalAPI {
    PagedPayload<EndUserPayload> getUserList(Pageable pageable);

    UserDetailsPayload getUserDetails(UUID userId );

    EndUserPayload getUserByUsername(String username);

    EndUserPayload getUserByResetCode(String resetCode);

    EndUserPayload initiateForgotPassword(String username);

    EndUserPayload resetPassword(String resetCode, String newPassword);

    EndUserPayload getUserById(UUID id);
}
