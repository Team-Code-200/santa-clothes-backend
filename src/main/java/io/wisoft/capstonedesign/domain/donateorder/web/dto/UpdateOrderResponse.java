package io.wisoft.capstonedesign.domain.donateorder.web.dto;

import io.wisoft.capstonedesign.domain.donateorder.persistence.DonateOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderResponse {

    private Long id;

    private String text;

    public UpdateOrderResponse(DonateOrder donateOrder) {
        this.id = donateOrder.getId();
        this.text = donateOrder.getText();
    }
}
