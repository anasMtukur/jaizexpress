package com.skylabng.jaizexpress.payload;

import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.provider.ProviderPayload;
import com.skylabng.jaizexpress.service.ServicePayload;

import java.util.List;

public record ProviderDetailsPayload(
        ProviderPayload organization,
        List<EndUserPayload> users,
        List<ServicePayload> services
) {
}
