package io.wisoft.capstonedesign.domain.user.web.dto;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserResponse {

    private String nickname;

    private int point;

    private String profileImage;

    public GetUserResponse(User user) {
        this.nickname = user.getNickname();
        this.point = user.getPoint();
        this.profileImage = user.getProfileImage();
    }
}
