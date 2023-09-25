package io.wisoft.capstonedesign.domain.address.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAddressRequest(
        @NotBlank String postcode,
        @NotBlank String postAddress,
        @NotBlank String detailAddress,
        String extraAddress,
        Long id
) { }
