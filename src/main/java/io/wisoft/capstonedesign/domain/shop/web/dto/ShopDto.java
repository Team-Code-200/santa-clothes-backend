package io.wisoft.capstonedesign.domain.shop.web.dto;

import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopDto {

    private String title;

    private int price;

    private String image;

    private String body;

    public ShopDto(Shop shop) {
        this.title = shop.getTitle();
        this.price = shop.getPrice();
        this.image = shop.getImage();
        this.body = shop.getBody();
    }
}
