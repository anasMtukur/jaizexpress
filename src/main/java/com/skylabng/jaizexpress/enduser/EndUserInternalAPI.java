package com.skylabng.jaizexpress.enduser;

import java.util.UUID;

public interface EndUserInternalAPI {

    EndUserPayload save(EndUserPayload endUserPayload);

    boolean checkUserExist( String username, String email, String mobile );

    EndUserPayload getUserByEmailOrMobile(String email, String mobile);

    EndUserPayload getUserByUsername(String username);

    EndUserPayload getUserByResetCode(String resetCode);

    EndUserPayload initiateForgotPassword(String username);

    EndUserPayload resetPassword(String resetCode, String newPassword);

    EndUserPayload getUserById(UUID id);

}
