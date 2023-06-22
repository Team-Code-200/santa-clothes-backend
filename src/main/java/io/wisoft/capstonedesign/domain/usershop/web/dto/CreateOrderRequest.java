package io.wisoft.capstonedesign.domain.usershop.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateOrderRequest(@NotBlank String text, Long infoId, Long shopId, Long userId) {
}
