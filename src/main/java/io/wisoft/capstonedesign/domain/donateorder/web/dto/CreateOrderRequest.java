package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotEmpty
    private String text;

    private Long infoId;

    private Long donateId;

    private Long userId;

    public static CreateOrderRequest newInstance(
            final String text,
            final Long infoId,
            final Long donateId,
            final Long userId
    ) {
        CreateOrderRequest request = new CreateOrderRequest();
        request.text = text;
        request.infoId = infoId;
        request.donateId = donateId;
        request.userId = userId;

        return request;
    }
}
