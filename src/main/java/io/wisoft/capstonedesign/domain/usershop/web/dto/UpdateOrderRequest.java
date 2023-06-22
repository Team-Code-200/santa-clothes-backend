package io.wisoft.capstonedesign.domain.usershop.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateOrderRequest(@NotBlank String text, Long orderId) {
}
