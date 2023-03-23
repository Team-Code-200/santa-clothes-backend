package io.wisoft.capstonedesign.global.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotEmpty
    private String nickname;
}
