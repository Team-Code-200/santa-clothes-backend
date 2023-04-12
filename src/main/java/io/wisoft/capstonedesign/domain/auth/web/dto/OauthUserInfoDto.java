package io.wisoft.capstonedesign.domain.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OauthUserInfoDto {

    private String nickname;
    private String oauthId;

    private String email;

    private String profileImage;
}
