package io.wisoft.capstonedesign.domain.findorder.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrderRequest(@NotBlank String text, Long orderId) {
}
