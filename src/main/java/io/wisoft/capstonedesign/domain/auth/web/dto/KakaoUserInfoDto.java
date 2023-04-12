package io.wisoft.capstonedesign.domain.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoUserInfoDto {

    private String nickname;
    private Long kakaoId;

    private String email;

    private String profileImage;
}
