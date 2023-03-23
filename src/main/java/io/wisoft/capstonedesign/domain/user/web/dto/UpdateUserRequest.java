package io.wisoft.capstonedesign.domain.user.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotEmpty
    private String nickname;
}
