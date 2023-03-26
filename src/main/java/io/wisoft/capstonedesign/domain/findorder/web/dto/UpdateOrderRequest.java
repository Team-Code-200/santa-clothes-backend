package io.wisoft.capstonedesign.domain.findorder.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {

    @NotEmpty
    private String text;

    private Long orderId;

    public static UpdateOrderRequest newInstance(
            String text,
            Long orderId
    ) {
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.text = text;
        request.orderId = orderId;

        return request;
    }
}
