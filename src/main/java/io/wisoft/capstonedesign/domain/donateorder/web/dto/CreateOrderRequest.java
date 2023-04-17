package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(@NotBlank String text, Long infoId, Long donateId, Long userId) {
}
