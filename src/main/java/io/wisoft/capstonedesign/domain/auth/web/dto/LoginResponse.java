package io.wisoft.capstonedesign.domain.auth.web.dto;

import io.wisoft.capstonedesign.global.enumerated.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

    private Long id;
    private String nickname;
    private String email;
    private String profileImage;
    private int point;
    private Role role;
    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponse(
            final Long id,
            final String nickname,
            final String email,
            final String profileImage,
            final int point,
            final Role role,
            final String tokenType,
            final String accessToken,
            final String refreshToken
    ) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.point = point;
        this.role = role;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
