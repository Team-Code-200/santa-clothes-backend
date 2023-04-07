package io.wisoft.capstonedesign.domain.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserResponse {

    private String nickname;

    private int point;

    private String profileImage;
}
