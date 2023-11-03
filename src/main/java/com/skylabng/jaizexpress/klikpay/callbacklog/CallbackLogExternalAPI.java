package com.skylabng.jaizexpress.klikpay.callbacklog;

import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.data.domain.Pageable;

public interface CallbackLogExternalAPI {
    PagedPayload<CallbackLogPayload> getCallbackList(Pageable pageable);
}
