package io.wisoft.capstonedesign.domain.information.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateInformationRequest(@NotBlank String username, @NotBlank String address, @NotBlank String phoneNumber, Long userId) {
}
