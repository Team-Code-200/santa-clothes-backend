package io.wisoft.capstonedesign.domain.user.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUserRequest {

    @NotEmpty
    private String nickname;
}
