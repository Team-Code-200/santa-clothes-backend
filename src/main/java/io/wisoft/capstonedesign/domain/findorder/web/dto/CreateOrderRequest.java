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
            String text,
            Long infoId,
            Long findId,
            Long userId
    ) {
        CreateOrderRequest request = new CreateOrderRequest();
        request.text = text;
        request.infoId = infoId;
        request.findId = findId;
        request.userId = userId;

        return request;
    }
}
