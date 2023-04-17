package io.wisoft.capstonedesign.domain.usershop.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(@NotBlank String text, Long infoId, Long shopId, Long userId) {
}
