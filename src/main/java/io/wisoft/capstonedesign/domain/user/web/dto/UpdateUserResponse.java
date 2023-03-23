package io.wisoft.capstonedesign.domain.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserResponse {

    private Long id;
    private String nickname;
}
