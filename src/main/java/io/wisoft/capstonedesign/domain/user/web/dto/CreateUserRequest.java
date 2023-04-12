package io.wisoft.capstonedesign.domain.user.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotEmpty
    private String oauthId;

    @Email
    @NotEmpty
    private String email;

    private String profileImage;

    private int point;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String userRole;
}
