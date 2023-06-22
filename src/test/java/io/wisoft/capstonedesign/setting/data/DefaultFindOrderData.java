package io.wisoft.capstonedesign.setting.data;

import io.wisoft.capstonedesign.domain.findorder.web.dto.CreateOrderRequest;

public class DefaultFindOrderData {

    public static CreateOrderRequest createDefaultOrder(Long infoId,
                                                        Long findId,
                                                        Long userId) {
        return CreateOrderRequest.builder()
                .text("배송전 문자주세요")
                .infoId(infoId)
                .findId(findId)
                .userId(userId)
                .build();
    }
}
