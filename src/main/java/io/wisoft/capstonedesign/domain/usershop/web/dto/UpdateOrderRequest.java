package io.wisoft.capstonedesign.domain.usershop.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrderRequest(@NotBlank String text, Long orderId) {
}
