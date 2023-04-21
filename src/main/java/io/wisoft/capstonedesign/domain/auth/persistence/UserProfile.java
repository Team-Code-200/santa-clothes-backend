package io.wisoft.capstonedesign.domain.auth.persistence;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import io.wisoft.capstonedesign.global.enumerated.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile {

    private final String oauthId;
    private final String email;
    private final String nickname;
    private final String profileImage;

    @Builder
    public UserProfile(final String oauthId, final String email, final String nickname, final String profileImage) {
        this.oauthId = oauthId;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public User toUser() {
        return User.builder()
                .oauthId(oauthId)
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .point(0)
                .userRole(Role.GENERAL)
                .build();
    }
}
