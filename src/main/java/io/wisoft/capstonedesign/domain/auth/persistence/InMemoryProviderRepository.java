package io.wisoft.capstonedesign.domain.auth.persistence;

import io.wisoft.capstonedesign.domain.auth.application.OauthProvider;

import java.util.HashMap;
import java.util.Map;

public class InMemoryProviderRepository {

    private final Map<String, OauthProvider> providers;

    public InMemoryProviderRepository(final Map<String, OauthProvider> providers) {
        this.providers = new HashMap<>(providers);
    }

    public OauthProvider findByProviderName(final String nickname) {
        return providers.get(nickname);
    }
}
