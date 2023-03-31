package io.wisoft.capstonedesign.domain.shop.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateShopRequest {

    @NotEmpty
    private String title;

    private int price;

    @NotEmpty
    private String image;

    private String body;

    private Long userId;

    public static CreateShopRequest newInstance(
            final String title,
            final int price,
            final String image,
            final String body,
            final Long userId
    ) {
        CreateShopRequest request = new CreateShopRequest();
        request.title = title;
        request.price = price;
        request.image = image;
        request.body = body;
        request.userId = userId;

        return request;
    }
}
