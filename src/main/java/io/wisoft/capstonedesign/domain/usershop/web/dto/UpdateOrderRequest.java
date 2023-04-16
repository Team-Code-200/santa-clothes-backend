package io.wisoft.capstonedesign.domain.usershop.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateOrderRequest {

    @NotEmpty
    private String text;

    private Long orderId;
}
