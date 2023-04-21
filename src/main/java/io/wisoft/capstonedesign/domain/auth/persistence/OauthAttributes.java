package io.wisoft.capstonedesign.domain.auth.persistence;

import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {

    KAKAO("kakao") {

        @Override
        public UserProfile of(final Map<String, Object> attributes) {

            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            return UserProfile.builder()
                    .oauthId(String.valueOf(attributes.get("id")))
                    .email((String) kakaoAccount.get("email"))
                    .nickname((String) profile.get("nickname"))
                    .profileImage((String) profile.get("profile_image_url"))
                    .build();
        }
    },

    NAVER("naver") {

        @Override
        public UserProfile of(final Map<String, Object> attributes) {

            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            return UserProfile.builder()
                    .oauthId((String) (response.get("id")))
                    .email((String) (response.get("email")))
                    .nickname((String) response.get("nickname"))
                    .profileImage((String) response.get("profile_image"))
                    .build();
        }
    },

    GOOGLE("google") {

        @Override
        public UserProfile of(final Map<String, Object> attributes) {

            return UserProfile.builder()
                    .oauthId((String) attributes.get("sub"))
                    .email((String) attributes.get("email"))
                    .nickname((String) attributes.get("name"))
                    .profileImage((String) attributes.get("picture"))
                    .build();
        }
    };

    private final String providerName;

    OauthAttributes(final String name) {
        this.providerName = name;
    }

    public static UserProfile extract(final String providerName, final Map<String, Object> attributes) {

        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of(attributes);
    }

    public abstract UserProfile of(final Map<String, Object> attributes);
}
