package io.wisoft.capstonedesign.domain.findorder.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateOrderRequest(@NotBlank String text, Long orderId) {
}
