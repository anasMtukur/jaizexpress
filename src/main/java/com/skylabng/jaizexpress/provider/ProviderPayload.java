package com.skylabng.jaizexpress.provider;

import java.util.UUID;

public record ProviderPayload(
        UUID id,
        String title,
        String logo,
        String email,
        String mobile,
        String url
) {
}
