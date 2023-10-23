package com.skylabng.jaizexpress.enduser;

import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.data.domain.Pageable;

public interface EndUserExternalAPI {
    PagedPayload<EndUserPayload> getUserList(Pageable pageable);
}
