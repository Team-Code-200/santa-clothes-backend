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
            String title,
            int price,
            String image,
            String body,
            Long userId
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