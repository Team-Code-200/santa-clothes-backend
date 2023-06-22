package io.wisoft.capstonedesign.domain.shop.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateShopRequest(@NotBlank String title, @NotNull int price, @NotBlank String image, @NotBlank String body, Long shopId) {
}
