package io.wisoft.capstonedesign.domain.user.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserRequest(
        @NotBlank String oauthId,
        @Email @NotBlank String email,
        @NotBlank String profileImage,
        @NotNull int point,
        @NotBlank String nickname,
        @NotBlank String userRole) {
}
