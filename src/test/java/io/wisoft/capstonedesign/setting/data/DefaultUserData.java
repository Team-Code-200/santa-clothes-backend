package io.wisoft.capstonedesign.setting.data;

import io.wisoft.capstonedesign.domain.user.web.dto.CreateUserRequest;
import io.wisoft.capstonedesign.global.enumerated.Role;

public class DefaultUserData {
    public static CreateUserRequest createDefaultUser() {

        return CreateUserRequest.builder()
                .oauthId("1")
                .email("jinwon@gmail.com")
                .profileImage("profile.png")
                .point(1000)
                .nickname("jinwon")
                .userRole(String.valueOf(Role.GENERAL))
                .build();
    }
}
