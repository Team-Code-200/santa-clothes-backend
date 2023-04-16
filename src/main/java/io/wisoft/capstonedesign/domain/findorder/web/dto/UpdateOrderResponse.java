package io.wisoft.capstonedesign.domain.findorder.web.dto;

import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateOrderResponse {

    private Long id;

    private String text;

    public UpdateOrderResponse(FindOrder findOrder) {
        this.id = findOrder.getId();
        this.text = findOrder.getText();
    }
}
