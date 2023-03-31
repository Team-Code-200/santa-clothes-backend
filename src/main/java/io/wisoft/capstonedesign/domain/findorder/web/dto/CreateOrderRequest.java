package io.wisoft.capstonedesign.domain.findorder.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotEmpty
    private String text;

    private Long infoId;

    private Long findId;

    private Long userId;

    public static CreateOrderRequest newInstance(
            final String text,
            final Long infoId,
            final Long findId,
            final Long userId
    ) {
        CreateOrderRequest request = new CreateOrderRequest();
        request.text = text;
        request.infoId = infoId;
        request.findId = findId;
        request.userId = userId;

        return request;
    }
}
