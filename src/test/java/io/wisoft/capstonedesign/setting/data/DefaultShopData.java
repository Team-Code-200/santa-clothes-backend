package io.wisoft.capstonedesign.setting.data;

import io.wisoft.capstonedesign.domain.shop.web.dto.CreateShopRequest;

public class DefaultShopData {

    public static CreateShopRequest createDefaultShop(Long userId) {

        return CreateShopRequest.builder()
                .title("라면 한 박스")
                .price(1000)
                .image("ramen.jpg")
                .body("포인트로 뜨끈한 라면 한 박스 가져가세요!")
                .userId(userId)
                .build();
    }
}
