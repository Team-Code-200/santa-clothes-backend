package io.wisoft.capstonedesign.domain.user.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank String oauthId,
        @Email String email,
        @NotBlank String profileImage,
        @NotNull int point,
        @NotBlank String nickname,
        @NotBlank String userRole) {
}
