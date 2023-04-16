package io.wisoft.capstonedesign.domain.user.web.dto;

import io.wisoft.capstonedesign.domain.user.persistence.User;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUserResponse {

    private Long id;
    private String nickname;

    public UpdateUserResponse(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
