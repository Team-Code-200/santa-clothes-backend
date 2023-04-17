package io.wisoft.capstonedesign.domain.user.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(@NotBlank String nickname) {
}
