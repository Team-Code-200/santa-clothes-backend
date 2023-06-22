package io.wisoft.capstonedesign.domain.findorder.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateOrderRequest(@NotBlank String text, Long infoId, Long findId, Long userId) {
}
