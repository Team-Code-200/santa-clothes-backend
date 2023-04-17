package io.wisoft.capstonedesign.domain.find.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateFindRequest(@NotBlank String title, String image, @NotBlank String text, String tag, Long userId) {
}
