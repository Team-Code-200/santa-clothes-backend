package io.wisoft.capstonedesign.global.config;

import io.wisoft.capstonedesign.domain.auth.application.OauthAdapter;
import io.wisoft.capstonedesign.domain.auth.application.OauthProvider;
import io.wisoft.capstonedesign.domain.auth.persistence.InMemoryProviderRepository;
import io.wisoft.capstonedesign.domain.auth.persistence.OauthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

    private final OauthProperties properties;

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(properties);
        return new InMemoryProviderRepository(providers);
    }
}
