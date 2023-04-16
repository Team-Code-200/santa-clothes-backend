package io.wisoft.capstonedesign.domain.usershop.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateOrderRequest {

    @NotEmpty
    private String text;

    private Long infoId;

    private Long shopId;

    private Long userId;
}
