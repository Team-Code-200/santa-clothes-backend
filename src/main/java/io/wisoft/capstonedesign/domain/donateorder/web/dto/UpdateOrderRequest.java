package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrderRequest(@NotBlank String text, Long orderId) {
}
