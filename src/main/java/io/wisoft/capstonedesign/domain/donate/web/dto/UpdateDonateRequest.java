package io.wisoft.capstonedesign.domain.donate.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateDonateRequest(@NotBlank String title, String image, @NotBlank String text, String tag, Long donateId) {
}
