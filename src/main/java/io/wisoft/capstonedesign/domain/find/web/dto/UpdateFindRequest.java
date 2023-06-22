package io.wisoft.capstonedesign.domain.find.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateFindRequest(@NotBlank String title, String image, @NotBlank String text, String tag, Long findId) {
}
