package io.wisoft.capstonedesign.domain.auth.application;

import io.wisoft.capstonedesign.domain.auth.persistence.OauthProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthAdapter {

    public static Map<String, OauthProvider> getOauthProviders(final OauthProperties properties) {

        final Map<String, OauthProvider> oauthProvider = new HashMap<>();

        properties.getUser().forEach((key, value) -> oauthProvider.put(key, new OauthProvider(value, properties.getProvider().get(key))));
        return oauthProvider;
    }
}
