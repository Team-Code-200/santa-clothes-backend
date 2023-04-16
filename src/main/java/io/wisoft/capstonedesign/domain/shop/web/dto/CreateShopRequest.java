package io.wisoft.capstonedesign.domain.shop.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateShopRequest {

    @NotEmpty
    private String title;

    private int price;

    @NotEmpty
    private String image;

    private String body;

    private Long userId;
}
