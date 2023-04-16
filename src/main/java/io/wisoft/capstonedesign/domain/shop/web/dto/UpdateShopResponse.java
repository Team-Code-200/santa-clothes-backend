package io.wisoft.capstonedesign.domain.shop.web.dto;

import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateShopResponse {

    private Long id;

    private String title;

    private int price;

    private String image;

    private String body;

    public UpdateShopResponse(Shop shop) {
        this.id = shop.getId();
        this.title = shop.getTitle();
        this.price = shop.getPrice();
        this.image = shop.getImage();
        this.body = shop.getBody();
    }
}
