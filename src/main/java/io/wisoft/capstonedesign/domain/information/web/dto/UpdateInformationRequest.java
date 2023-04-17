package io.wisoft.capstonedesign.domain.information.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateInformationRequest(@NotBlank String username, @NotBlank String address, @NotBlank String phoneNumber, Long infoId) {
}
