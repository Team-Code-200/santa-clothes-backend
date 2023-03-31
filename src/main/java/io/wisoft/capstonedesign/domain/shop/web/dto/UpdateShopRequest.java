package io.wisoft.capstonedesign.domain.shop.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShopRequest {

    @NotEmpty
    private String title;

    private int price;

    @NotEmpty
    private String image;

    @NotEmpty
    private String body;

    private Long shopId;

    public static UpdateShopRequest newInstance(
            final String title,
            final int price,
            final String image,
            final String body,
            final Long shopId
    ) {
        UpdateShopRequest request = new UpdateShopRequest();
        request.title = title;
        request.price = price;
        request.image = image;
        request.body = body;
        request.shopId = shopId;

        return request;
    }
}
