package io.wisoft.capstonedesign.domain.findorder.web.dto;

import io.wisoft.capstonedesign.domain.findorder.persistence.FindOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderResponse {

    private Long id;

    private String text;

    public UpdateOrderResponse(FindOrder findOrder) {
        this.id = findOrder.getId();
        this.text = findOrder.getText();
    }
}
