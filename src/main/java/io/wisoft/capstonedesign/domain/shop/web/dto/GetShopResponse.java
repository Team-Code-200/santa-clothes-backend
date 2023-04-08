package io.wisoft.capstonedesign.domain.shop.web.dto;

import io.wisoft.capstonedesign.domain.shop.persistence.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetShopResponse {

    private String title;

    private int price;

    private String image;

    private String body;

    private LocalDateTime createdDate;

    public GetShopResponse(Shop shop) {
        this.title = shop.getTitle();
        this.price = shop.getPrice();
        this.image = shop.getImage();
        this.body = shop.getBody();
        this.createdDate = shop.getCreatedDate();
    }
}
