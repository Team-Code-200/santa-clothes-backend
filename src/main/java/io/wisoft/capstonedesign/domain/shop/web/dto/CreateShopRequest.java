package io.wisoft.capstonedesign.domain.shop.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateShopRequest(@NotBlank String title, @NotNull int price, @NotBlank String image, String body, Long userId) {
}
