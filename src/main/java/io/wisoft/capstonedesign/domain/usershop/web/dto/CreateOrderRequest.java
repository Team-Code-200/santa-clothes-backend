package io.wisoft.capstonedesign.domain.usershop.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotEmpty
    private String text;

    private Long infoId;

    private Long shopId;

    private Long userId;

    public static CreateOrderRequest newInstance(
            final String text,
            final Long infoId,
            final Long shopId,
            final Long userId
    ) {
        CreateOrderRequest request = new CreateOrderRequest();
        request.text = text;
        request.infoId = infoId;
        request.shopId = shopId;
        request.userId = userId;

        return request;
    }
}
