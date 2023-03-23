package io.wisoft.capstonedesign.global.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserResponse {

    private Long id;
    private String nickname;
}
