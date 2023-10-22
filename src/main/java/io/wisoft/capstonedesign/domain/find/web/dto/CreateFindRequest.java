package io.wisoft.capstonedesign.domain.find.web.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateFindRequest(@NotBlank String title, @Nullable String image, @NotBlank String text, String tag, Long userId) {
}
