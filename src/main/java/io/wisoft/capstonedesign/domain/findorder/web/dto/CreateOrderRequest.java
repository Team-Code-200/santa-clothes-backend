package io.wisoft.capstonedesign.domain.findorder.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateOrderRequest {

    @NotEmpty
    private String text;

    private Long infoId;

    private Long findId;

    private Long userId;
}
