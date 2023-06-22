package io.wisoft.capstonedesign.domain.user.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(@NotBlank(message = "닉네임을 입력해주세요.") String nickname) {
}
