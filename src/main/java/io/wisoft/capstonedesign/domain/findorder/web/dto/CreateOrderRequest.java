package io.wisoft.capstonedesign.domain.findorder.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(@NotBlank String text, Long infoId, Long findId, Long userId) {
}
