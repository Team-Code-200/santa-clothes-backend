package io.wisoft.capstonedesign.domain.usershop.web.dto;

import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateOrderResponse {

    private Long id;

    private String text;

    public UpdateOrderResponse(UserShop userShop) {
        this.id = userShop.getId();
        this.text = userShop.getText();
    }
}
