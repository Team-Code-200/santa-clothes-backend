package io.wisoft.capstonedesign.domain.auth.application;

import io.wisoft.capstonedesign.domain.auth.persistence.InMemoryProviderRepository;
import io.wisoft.capstonedesign.domain.auth.persistence.OauthAttributes;
import io.wisoft.capstonedesign.domain.auth.persistence.UserProfile;
import io.wisoft.capstonedesign.domain.auth.web.dto.LoginResponse;
import io.wisoft.capstonedesign.domain.auth.web.dto.OauthTokenResponse;
import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.domain.user.persistence.UserRepository;
import io.wisoft.capstonedesign.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final int LOGIN_EXPIRED_TIME = 24;

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    public LoginResponse login(final String providerName, final String code) {

        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        OauthTokenResponse tokenResponse = getToken(code, provider);

        UserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);

        User user = saveOrUpdate(userProfile);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisTemplate.opsForValue().set(user.getNickname(), refreshToken, LOGIN_EXPIRED_TIME, TimeUnit.HOURS);

        return LoginResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .point(user.getPoint())
                .role(user.getUserRole())
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private User saveOrUpdate(final UserProfile userProfile) {

        User user = userRepository.findByOauthId(userProfile.getOauthId())
                .map(entity -> entity.updateAll(
                        userProfile.getNickname(), userProfile.getEmail(), userProfile.getProfileImage()))
                .orElseGet(userProfile::toUser);

        return userRepository.save(user);
    }

    private UserProfile getUserProfile(final String providerName, final OauthTokenResponse tokenResponse, final OauthProvider provider) {

        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
        return OauthAttributes.extract(providerName, userAttributes);
    }

    private Map<String, Object> getUserAttributes(final OauthProvider provider, final OauthTokenResponse tokenResponse) {

        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUri())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private OauthTokenResponse getToken(final String code, final OauthProvider provider) {

        return WebClient.create()
                .post()
                .uri(provider.getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(final String code, final OauthProvider provider) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());

        return formData;
    }
}
