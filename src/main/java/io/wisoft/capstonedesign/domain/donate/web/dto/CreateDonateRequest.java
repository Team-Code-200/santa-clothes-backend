package io.wisoft.capstonedesign.domain.donate.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDonateRequest(@NotBlank String title, String image, @NotBlank String text, String tag, Long userId) {
}
