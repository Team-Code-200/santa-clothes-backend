package io.wisoft.capstonedesign.setting.data;

import io.wisoft.capstonedesign.domain.donateorder.web.dto.CreateOrderRequest;

public class DefaultDonateOrderData {

    public static CreateOrderRequest createDefaultOrder(Long infoId,
                                                        Long donateId,
                                                        Long userId) {
        return CreateOrderRequest.builder()
                .text("배송전 문자주세요")
                .infoId(infoId)
                .donateId(donateId)
                .userId(userId)
                .build();
    }
}
