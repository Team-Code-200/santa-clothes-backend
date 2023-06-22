package io.wisoft.capstonedesign.setting.data;

import io.wisoft.capstonedesign.domain.usershop.web.dto.CreateOrderRequest;

public class DefaultShopOrderData {

    public static CreateOrderRequest createDefaultOrder(Long infoId,
                                                        Long shopId,
                                                        Long userId) {
        return CreateOrderRequest.builder()
                .text("배송 전 문자 부탁드립니다")
                .infoId(infoId)
                .shopId(shopId)
                .userId(userId)
                .build();
    }
}
